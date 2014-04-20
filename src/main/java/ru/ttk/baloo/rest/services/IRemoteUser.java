package ru.ttk.baloo.rest.services;

import java.io.Serializable;

/**
 *
 */
public interface IRemoteUser extends Serializable {

    public String getUserName();

    public String getPassword();

    public String getPersonURI();
}
