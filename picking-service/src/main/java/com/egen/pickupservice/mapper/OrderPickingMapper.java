package com.egen.pickupservice.mapper;

import com.egen.pickupservice.model.dto.PickOrderDTO;
import com.egen.pickupservice.model.dto.client.EmployeePerformanceDTO;
import com.egen.pickupservice.model.entity.EmployeePerformance;
import com.egen.pickupservice.model.entity.PickOrder;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class provides DTO to entity and vice versa conversion.
 */
@Mapper(mappingControl = DeepClone.class)
public interface OrderPickingMapper {

    OrderPickingMapper MAPPER = Mappers.getMapper(OrderPickingMapper.class );

    PickOrderDTO toPickOrderDTO(PickOrder pickOrder);
    PickOrder toPickOrder(PickOrderDTO pickOrderDTO);
    EmployeePerformanceDTO toEmployeePerformance(EmployeePerformance employeePerformance);
}
