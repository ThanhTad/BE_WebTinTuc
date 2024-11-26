package com.sport.news.exception;

public class BusinessOperationException extends RuntimeException {

    public BusinessOperationException(String message) {
        super(message);
    }

    public BusinessOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
