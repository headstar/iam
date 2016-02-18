package com.headstartech.iam.common.exceptions;

import java.net.HttpURLConnection;

/**
 * Created by per on 2/18/16.
 */
public class IAMBadRequestException extends IAMException {

    public IAMBadRequestException(String message) {
        super(HttpURLConnection.HTTP_BAD_REQUEST, message);
    }
}
