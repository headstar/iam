package com.headstartech.iam.common.exceptions;

public class IAMNotFoundException extends IAMException {

    public IAMNotFoundException() {
    }

    public IAMNotFoundException(String message) {
        super(message);
    }

    public IAMNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IAMNotFoundException(Throwable cause) {
        super(cause);
    }
}
