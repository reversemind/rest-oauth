package ru.ttk.baloo.rest.services;

import org.apache.commons.lang3.StringUtils;
import ru.ttk.baloo.rest.security.oauth.SampleUser;

import java.io.Serializable;

/**
 *
 */
public interface IRemoteUserFinder extends Serializable {

    public IRemoteUser findUser(String userName, String password);

    public IRemoteUser findUser(String userName);

}
