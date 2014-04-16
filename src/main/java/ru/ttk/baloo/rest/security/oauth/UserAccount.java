package ru.ttk.baloo.rest.security.oauth;

import java.io.Serializable;

/**
 *
 */
public class UserAccount implements Serializable {

    private Object principal;
    private Object credentials;
    private String personUri;

    public UserAccount(Object principal, Object credentials) {
        this.principal = principal;
        this.credentials = credentials;
        this.personUri = null;
    }

    public UserAccount(Object principal, Object credentials, String personUri) {
        this.principal = principal;
        this.credentials = credentials;
        this.personUri = personUri;
    }

    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public Object getCredentials() {
        return credentials;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public String getPersonUri() {
        return personUri;
    }

    public void setPersonUri(String personUri) {
        this.personUri = personUri;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "principal=" + principal +
                ", credentials=" + credentials +
                ", personUri='" + personUri + '\'' +
                '}';
    }
}
