package com.headstartech.iam.common.exceptions;

public class IAMPreconditionException extends IAMException {

    public IAMPreconditionException() {
    }

    public IAMPreconditionException(String message) {
        super(message);
    }

    public IAMPreconditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IAMPreconditionException(Throwable cause) {
        super(cause);
    }
}
