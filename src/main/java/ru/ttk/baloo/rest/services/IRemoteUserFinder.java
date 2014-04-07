package ru.ttk.baloo.rest.services;

import java.io.Serializable;

/**
 *
 */
public interface IRemoteUserFinder extends Serializable {

    public IRemoteUser findUser(String userName, String password);

    public IRemoteUser findUser(String userName);

}
