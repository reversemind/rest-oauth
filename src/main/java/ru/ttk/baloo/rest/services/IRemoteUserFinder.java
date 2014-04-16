package ru.ttk.baloo.rest.services;

import org.springframework.security.oauth2.provider.ClientDetails;

import java.io.Serializable;

/**
 *
 */
public interface IRemoteUserFinder extends Serializable {

    public IRemoteUser findUser(String userName, String password);

//    public IRemoteUser findUser(String userName);

    public ClientDetails findClient(String clientName);
}
