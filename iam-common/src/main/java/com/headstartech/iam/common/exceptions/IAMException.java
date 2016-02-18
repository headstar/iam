package com.headstartech.iam.common.exceptions;

public class IAMException extends Exception {

    public IAMException() {
    }

    public IAMException(String message) {
        super(message);
    }

    public IAMException(String message, Throwable cause) {
        super(message, cause);
    }

    public IAMException(Throwable cause) {
        super(cause);
    }
}
