package com.ecom.task.common.exception;

public class OverLimitException extends RuntimeException {
    public OverLimitException(String message) {
        super(message);
    }
}
