package com.headstartech.iam.common.exceptions;

import java.net.HttpURLConnection;

public class IAMPreconditionException extends IAMException {

    public IAMPreconditionException(String message) {
        super(HttpURLConnection.HTTP_PRECON_FAILED, message);
    }
}
