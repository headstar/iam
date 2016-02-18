package com.headstartech.iam.common.exceptions;

import java.net.HttpURLConnection;

public class IAMConflictException extends IAMException {

    public IAMConflictException(String message) {
        super(HttpURLConnection.HTTP_CONFLICT, message);
    }
}
