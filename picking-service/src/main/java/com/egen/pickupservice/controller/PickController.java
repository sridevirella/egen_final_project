package com.egen.pickupservice.controller;

import com.egen.pickupservice.model.dto.EmployeePerfStatsDTO;
import com.egen.pickupservice.model.dto.PickOrderDTO;
import com.egen.pickupservice.model.dto.PickStatusDTO;
import com.egen.pickupservice.response.*;
import com.egen.pickupservice.service.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/orders/pick")
public class PickController {

    private final PickingService pickingService;
    private final EmployeePerformanceService employeePerformanceService;

    public PickController(PickingService pickingService,EmployeePerformanceService employeePerformanceService) {
        this.pickingService = pickingService;
        this.employeePerformanceService = employeePerformanceService;
    }

    /**
     * An endpoint to send order pickup status
     */
    @PostMapping(value = "/status")
    @ApiOperation(value = "update order picking status",
                  notes = "Returns either successful or unsuccessful message of picking status update operation.")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
                            @ApiResponse(code = 404, message = "Service not found"),
                            @ApiResponse(code = 200, message = "Successful update")})
    public Response<String> updatePickingStatus(@RequestBody PickStatusDTO pickStatus) {

        pickingService.updatePickingStatus(pickStatus);
        return  Response.<String>builder()
                .meta(ResponseMetadata.builder()
                        .statusCode(201)
                        .statusMessage(StatusMessage.SUCCESS.name()).build())
                .data("Order picking status successfully updated")
                .build();
    }

    /**
     * An endpoint to create single order picking
     */
    @PostMapping
    @ApiOperation(value = "create an order pickup (Single or batch)",
                  notes = "Returns created order data with assigned employee.")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
                            @ApiResponse(code = 404, message = "Service not found"),
                            @ApiResponse(code = 201, message = "Successful create")})
    public Response<PickOrderDTO> createOrderPicking(@RequestBody PickOrderDTO pickOrder) throws Exception {

        return  Response.<PickOrderDTO>builder()
                .meta(ResponseMetadata.builder()
                        .statusCode(201)
                        .statusMessage(StatusMessage.SUCCESS.name()).build())
                .data(pickingService.createOrderPicking(pickOrder))
                .build();
    }

    /**
     * An endpoint to get order picking status
     */
    @GetMapping(value = "/status/{id}")
    @ApiOperation(value = "Find order picking status",
                  notes = "Returns corresponding order status.")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
                            @ApiResponse(code = 404, message = "Service not found"),
                            @ApiResponse(code = 200, message = "Successful retrieval")})
    public Response<PickStatusDTO> getPickingStatus(@PathVariable("id") UUID pickId) {

        return   Response.<PickStatusDTO>builder()
                .meta(ResponseMetadata.builder()
                        .statusCode(201)
                        .statusMessage(StatusMessage.SUCCESS.name()).build())
                .data(pickingService.getPickingStatus(pickId))
                .build();
    }

    /**
     * An endpoint to cancel order picking
     */
    @PostMapping(value = "/cancel/{id}")
    @ApiOperation(value = "cancel single order pick",
                  notes = "Returns successful order pickup cancel message.")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
                            @ApiResponse(code = 404, message = "Service not found"),
                            @ApiResponse(code = 201, message = "Successful cancel")})
    public Response<String> cancelOrderPick(@PathVariable("id") UUID pickId) {

        pickingService.cancelOrderPick(pickId);

        return Response.<String>builder()
                .meta(ResponseMetadata.builder()
                        .statusCode(200)
                        .statusMessage(StatusMessage.SUCCESS.name()).build())
                .data("Order pickup successfully cancelled")
                .build();
    }

    /**
     * An endpoint to get employee performance statistics
     */
    @GetMapping("/getEmpPerformanceStat")
    @ApiOperation(value = "Get employee performance status",
                  notes = "Returns employee performance data.")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
                            @ApiResponse(code = 404, message = "Service not found"),
                            @ApiResponse(code = 200, message = "Successful create")})
    public Response<EmployeePerfStatsDTO> getPerformanceStat(@RequestParam String empId) {

        return  Response.<EmployeePerfStatsDTO>builder()
                .meta(ResponseMetadata.builder()
                        .statusCode(200)
                        .statusMessage(StatusMessage.SUCCESS.name()).build())
                .data(employeePerformanceService.getEmpPerformance(empId))
                .build();
    }
}
