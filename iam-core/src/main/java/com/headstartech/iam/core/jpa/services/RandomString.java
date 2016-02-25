package com.headstartech.iam.core.jpa.services;

import java.security.SecureRandom;

/**
 * Created by per on 7/15/14.
 */
public class RandomString {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    private RandomString() {}

    public static String randomString(int len)
    {
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String randomId() {
        return randomString(16);
    }


}
