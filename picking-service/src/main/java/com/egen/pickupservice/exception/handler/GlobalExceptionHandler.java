package com.egen.pickupservice.exception.handler;


import com.egen.pickupservice.exception.PickOrderServiceException;
import com.egen.pickupservice.exception.PickingOrderNotFoundException;
import com.egen.pickupservice.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response<?>> handleMissingParams(MissingServletRequestParameterException e) {

        String errorResponseMessage = e.getParameterName() + " request parameter is missing";
        log.error(e.getClass().getSimpleName(), errorResponseMessage);
        return buildResponse(StatusMessage.MISSING_REQUEST_PARAMETER, BAD_REQUEST, errorResponseMessage);
    }

    @ExceptionHandler(PickOrderServiceException.class)
    public ResponseEntity<Response<?>> handlePickOrderException(PickOrderServiceException e) {
        log.error(e.getClass().getSimpleName(), e.getMessage());
        return buildResponse(StatusMessage.INTERNAL_EXCEPTION, BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(PickingOrderNotFoundException.class)
    public ResponseEntity<Response<?>> handlePickingOrderNotFoundException(PickingOrderNotFoundException e) {
        log.error(e.getClass().getSimpleName(), e.getMessage());
        return buildResponse(StatusMessage.INTERNAL_EXCEPTION, BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<Response<?>> buildResponse(StatusMessage statusMessage, HttpStatus status, String errors)  {

        var response = Response.builder()
                .meta(ResponseMetadata.builder()
                        .statusMessage(statusMessage.name())
                        .statusCode(status.value())
                        .build())
                .data(errors)
                .build();
        return ResponseEntity.status(status)
                             .body(response);
    }
}