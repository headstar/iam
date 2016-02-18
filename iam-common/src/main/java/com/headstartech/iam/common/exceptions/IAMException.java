package com.headstartech.iam.common.exceptions;

public class IAMException extends Exception {

    private final int errorCode;

    public IAMException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
