package com.egen.pickupservice.controller;

import com.egen.pickupservice.PickupServiceApplicationTests;
import com.egen.pickupservice.model.dto.PickOrderDTO;
import com.egen.pickupservice.service.PickingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PickControllerTest extends PickupServiceApplicationTests {

    @MockBean
    private PickingService pickingService;

    @Override
    @BeforeEach
    public void setup(){
        super.setup();
    }

    @Test
    public void testCreateOrderPicking()throws Exception {

        String uri = "/orders/pick";

        String requestJson = "{\n" +
                "  \"pickCreatedAt\": \"2021-07-01T11:02:27.250Z\",\n" +
                "  \"pickEmpId\": \"\",\n" +
                "  \"pickId\": \"\",\n" +
                "  \"pickItemQty\": 4,\n" +
                "  \"pickMethodType\": \"SINGLE\",\n" +
                "  \"pickOrderItemList\": [\n" +
                "    {\n" +
                "      \"pickId\": \"\",\n" +
                "      \"pickIsSubstAllowed\": true,\n" +
                "      \"pickItems\": [\n" +
                "        {\n" +
                "          \"pickItemAvailability\": true,\n" +
                "          \"pickItemHeight\": \"12\",\n" +
                "          \"pickItemId\": \"Item1\",\n" +
                "          \"pickItemLength\": \"56\",\n" +
                "          \"pickItemWeight\": \"60\",\n" +
                "          \"pickItemWidth\": \"555\",\n" +
                "          \"pickOrderItemId\": \"\",\n" +
                "          \"pickZone\": \"South\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"pickOrderItemHeight\": \"25\",\n" +
                "      \"pickOrderItemId\": \"OD1\",\n" +
                "      \"pickOrderItemLength\": \"21\",\n" +
                "      \"pickOrderItemName\": \"RFT\",\n" +
                "      \"pickOrderItemQty\": 5,\n" +
                "      \"pickOrderItemWidth\": \"1\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pickStatus\": \"OPEN\",\n" +
                "  \"pickStoreId\": \"store1\",\n" +
                "  \"pickToteCartId\": \"556\",\n" +
                "  \"pickToteId\": \"889\",\n" +
                "  \"pickWarehouseId\": \"789\"\n" +
                "}";
        PickOrderDTO pickOrderDTO = mock(PickOrderDTO.class);
        when(pickingService.createOrderPicking(Mockito.any(PickOrderDTO.class))).thenReturn(pickOrderDTO);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}