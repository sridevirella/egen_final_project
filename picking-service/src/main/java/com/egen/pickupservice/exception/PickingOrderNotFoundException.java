package com.egen.pickupservice.exception;

public class PickingOrderNotFoundException extends RuntimeException {
    public PickingOrderNotFoundException(String message) {
        super(message);
    }
}
