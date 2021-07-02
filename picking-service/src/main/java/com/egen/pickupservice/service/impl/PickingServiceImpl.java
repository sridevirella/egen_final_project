package com.egen.pickupservice.service.impl;

import com.egen.pickupservice.exception.PickOrderServiceException;
import com.egen.pickupservice.exception.PickingOrderNotFoundException;
import com.egen.pickupservice.model.dto.PickOrderDTO;
import com.egen.pickupservice.model.dto.PickStatusDTO;
import com.egen.pickupservice.mapper.OrderPickingMapper;
import com.egen.pickupservice.model.entity.*;
import com.egen.pickupservice.model.enums.*;
import com.egen.pickupservice.repository.*;
import com.egen.pickupservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PickingServiceImpl implements PickingService {

    private final PickOrderRepository pickOrderRepository;
    private final PickEmployeeRepository pickEmployeeRepository;
    private final OrderPickListener orderPickListener;
    private final PickStoreRepository pickStoreRepository;
    private final PickItemSubstRepository pickItemSubstRepository;

    public PickingServiceImpl(PickOrderRepository pickOrderRepository,
                              PickEmployeeRepository pickEmployeeRepository,
                              OrderPickListener orderPickListener,
                              PickStoreRepository pickStoreRepository,
                              PickItemSubstRepository pickItemSubstRepository) {

        this.pickOrderRepository = pickOrderRepository;
        this.pickEmployeeRepository = pickEmployeeRepository;
        this.orderPickListener = orderPickListener;
        this.pickStoreRepository = pickStoreRepository;
        this.pickItemSubstRepository = pickItemSubstRepository;
    }

    @Override
    public void updatePickingStatus(PickStatusDTO pickStatusDTO) {

        try {
            Optional<PickOrder> pickOrder = pickOrderRepository.findById(pickStatusDTO.getPickId());

            if (pickOrder.isPresent()) {

                   //update existing order picking status
                    pickOrder.get().setPickStatus(pickStatusDTO.getPickingStatus());
                    pickOrderRepository.save(pickOrder.get());

                    //set employee activities
                    if (pickStatusDTO.getPickingStatus().equals(PickingStatus.COMPLETED)) {

                        saveAndSendEmployeeActivities(pickOrder.get());
                        changeEmployeeAvailability(pickOrder.get());
                    }
                }
            else
                throw new PickingOrderNotFoundException("order picking id not found");

        } catch (Exception e) {
            log.error("Error occurred in updatePickingStatus: " + e.getMessage());
            throw new PickOrderServiceException(e.getMessage());
        }
    }

    /**
     * change employee occupied status
     */
    private void changeEmployeeAvailability(PickOrder pickOrder) {

        PickEmployee pickEmployee = pickEmployeeRepository.getById(pickOrder.getPickEmpId());
        pickEmployee.setPickEmpOccupied(false);
        pickEmployeeRepository.save(pickEmployee);
    }

    /**
     * save employee activities
     */
    private void saveAndSendEmployeeActivities(PickOrder pickOrder) {

        EmployeePerformance employeePerformance = new EmployeePerformance();
        employeePerformance.setPickEmpId(pickOrder.getPickEmpId());
        employeePerformance.setPickEmpPerfStartDtTm(pickOrder.getPickCreatedAt());
        employeePerformance.setPickEmpPerfEndDtTm(new Date());
        employeePerformance.setPickMethodType(pickOrder.getPickMethodType());

        //send employee performance details to kafka client
        if (orderPickListener != null)
            this.orderPickListener.onOrderPicked(employeePerformance);
    }

    /**
     * create single or batch order pick.
     */
    @Override
    public PickOrderDTO createOrderPicking(PickOrderDTO pickOrderDTO) {

        try {

            PickOrder pickOrder = OrderPickingMapper.MAPPER.toPickOrder(pickOrderDTO);

            PickEmployee unAssignedEmployee = assignEmployee(pickOrder.getPickStoreId());
            pickOrder.setPickEmpId(unAssignedEmployee.getPickEmpId());

            checkForSubstituteItem(pickOrder);
            return getCreatedNewPickOrder(pickOrder, unAssignedEmployee);

        } catch (Exception e) {
            log.error("Error occurred in createOrderPicking: {}" + e.getMessage());
            throw new PickOrderServiceException(e.getMessage());
        }
    }

    /**
     * create order pick and change employee availability to occupied
     */
    private PickOrderDTO getCreatedNewPickOrder(PickOrder pickOrder, PickEmployee unAssignedEmployee) {

        PickOrder newOrderPicking = pickOrderRepository.save(pickOrder);

        unAssignedEmployee.setPickEmpOccupied(true);        // change employee availability
        pickEmployeeRepository.save(unAssignedEmployee);
        return OrderPickingMapper.MAPPER.toPickOrderDTO(newOrderPicking);
    }

    /**
     * if item is not available check for the substitute items
     */
    private void checkForSubstituteItem(PickOrder pickOrder) {

        for (PickOrderItem pickOrderItem : pickOrder.getPickOrderItemList()) {

            if (pickOrderItem.isPickIsSubstAllowed()) {

                List<PickItem> availableItemList = pickOrderItem.getPickItems().stream().map(pickItem -> {

                    PickStoreItem pickStoreItem = pickStoreRepository.findByPickItemID(pickItem.getPickItemId());

                    if (!pickStoreItem.isItemAvailability()) {
                        PickItemSubstitute pickItemSubstitute = pickItemSubstRepository.findByPickItemId(pickItem.getPickItemId());
                        pickItem.setPickItemId(pickItemSubstitute.getPickSubstItemId());
                    }
                    return pickItem;
                }).collect(Collectors.toList());

                pickOrderItem.setPickItems(availableItemList);
            }
        }
    }

    /**
     * filter unoccupied employees and get the first available employee.
     */
    private PickEmployee assignEmployee(String storeId) {

        List<PickEmployee> employeeList = pickEmployeeRepository.findByStoreIdOrderByPickEmpIdAsc(storeId);
        Optional<PickEmployee> employee = employeeList.stream().filter(pickEmployee -> !pickEmployee.isPickEmpOccupied())
                                                               .findFirst();
       return employee.orElseThrow(() -> new PickingOrderNotFoundException("Picking order id not found"));
    }

    /**
     * Get order picking status.
     */
    @Override
    public PickStatusDTO getPickingStatus(UUID pickId) {

        Optional<PickOrder> pickOrder = pickOrderRepository.findById(pickId);

        return pickOrder.map(order -> PickStatusDTO.builder()
                                            .pickId(order.getPickId())
                                            .pickingStatus(order.getPickStatus()).build())
                       .orElseThrow(() -> new PickingOrderNotFoundException("picking order id not found"));
    }

    /**
     * if order pick exist cancel the order else throw exception
     */

    @Override
    public void cancelOrderPick(UUID pickId) {

        Optional<PickOrder> pickOrder = pickOrderRepository.findById(pickId);

        pickOrder.ifPresentOrElse(this::cancelPicking,
                                  () -> {throw new PickingOrderNotFoundException("picking order id not found");} );
    }

    /**
     * change order picking status and employee availability.
     */
    private void cancelPicking(PickOrder order) {

        if(!order.getPickStatus().equals(PickingStatus.COMPLETED))
            order.setPickStatus(PickingStatus.CANCELED);
        else throw new PickOrderServiceException("picking Order has already cancelled");

        PickEmployee employee = pickEmployeeRepository.findByPickEmpId(order.getPickEmpId());
        employee.setPickEmpOccupied(false);
        pickEmployeeRepository.save(employee);
    }
}
