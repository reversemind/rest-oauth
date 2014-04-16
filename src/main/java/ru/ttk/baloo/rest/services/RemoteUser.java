package ru.ttk.baloo.rest.services;

import java.io.Serializable;

/**
 *
 */
public class RemoteUser implements Serializable, IRemoteUser {

    private String userName;
    private String password;
    private String roles;
    private String personUri;

    public RemoteUser(String userName, String password, String roles, String personUri) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.personUri = personUri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPersonUri() {
        return personUri;
    }

    public void setPersonUri(String personUri) {
        this.personUri = personUri;
    }

    @Override
    public String toString() {
        return "RemoteUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", personUri='" + personUri + '\'' +
                '}';
    }
}
