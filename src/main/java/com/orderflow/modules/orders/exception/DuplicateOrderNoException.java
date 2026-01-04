package com.orderflow.modules.orders.exception;

public class DuplicateOrderNoException extends RuntimeException {

    public DuplicateOrderNoException(String message) {
        super(message);
    }
}
