package com.jupiter.community.exception;

public class CustomizeException extends RuntimeException {

    private Integer status;
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.status=errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }
}
