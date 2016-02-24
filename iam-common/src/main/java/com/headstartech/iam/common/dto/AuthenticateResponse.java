package com.headstartech.iam.common.dto;

import java.util.Set;

public class AuthenticateResponse {

    private boolean authenticationSuccessful;
    private Set<String> permissions;

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public boolean isAuthenticationSuccessful() {
        return authenticationSuccessful;
    }

    public void setAuthenticationSuccessful(boolean authenticationSuccessful) {
        this.authenticationSuccessful = authenticationSuccessful;
    }
}
