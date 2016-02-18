package com.headstartech.iam.common.exceptions;

import java.net.HttpURLConnection;

public class IAMNotFoundException extends IAMException {

    public IAMNotFoundException(String message) {
        super(HttpURLConnection.HTTP_NOT_FOUND, message);
    }
}
