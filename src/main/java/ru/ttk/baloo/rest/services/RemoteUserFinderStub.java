/**
 * Copyright (c) 2013-2014 Eugene Kalinin
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ttk.baloo.rest.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.BullShitPasswordEncoder;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import ru.ttk.baloo.remote.core.model.Account;
import ru.ttk.baloo.remote.core.model.Role;
import ru.ttk.baloo.remote.core.repository.AccountRepository;
import ru.ttk.baloo.remote.core.repository.RoleRepository;
import ru.ttk.baloo.rest.security.oauth.SampleUser;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Remote emulation
 */
@Service
public class RemoteUserFinderStub implements IRemoteUserFinder {

    private final static Logger LOG = LoggerFactory.getLogger(RemoteUserFinderStub.class);

    private final static boolean wrongMD5 = true;

    @Inject
    AccountRepository accountRepository;

    @Inject
    RoleRepository roleRepository;

    // TODO cache
    public IRemoteUser findUser(String userName, String password) {

//        LOG.debug("Going to find user:" + userName + " password:" + password);
        LOG.info("Getting account userName:" + userName + "| password:" + password + " wrongMD5:" + BullShitPasswordEncoder.wrongMD5(password) + "| rightMD5:" + DigestUtils.md5Hex(password));

        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {

            if (userName.equals(SampleUser.USERNAME) && password.equals(SampleUser.PASSWORD)) {
                return new RemoteUser(SampleUser.USERNAME, BullShitPasswordEncoder.wrongMD5(SampleUser.PASSWORD), "", "");
            }

            Account account = accountRepository.findByPrincipalNameAndPassword(userName, RemoteUserFinderStub.passwordViaMD5(password));
            LOG.info("Get from core account:" + account);

            if (account != null) {
                List<String> roleList = roleRepository.findRoleNamesByAccountId(account.getUuid());
                if (roleList != null && roleList.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String role : roleList) {
                        stringBuilder.append(role).append(";");
                    }
                    return new RemoteUser(account.getPrincipalName(), password, stringBuilder.toString(), account.getPersonUri());
                }
            }
        }
        return null;
    }

    // TODO Cache
    /**
     * Stub
     *
     * @param userName
     * @return
     */
    private RemoteUser findUser(String userName) {
        LOG.info("Going to find user:" + userName);

        if (StringUtils.isNotBlank(userName)) {
            if (userName.equals(SampleUser.USERNAME)) {
                return new RemoteUser(SampleUser.USERNAME, BullShitPasswordEncoder.wrongMD5(SampleUser.PASSWORD), "", "");
            }

            Account account = accountRepository.findByPrincipalName(userName);
            LOG.info("Get from core account:" + account);

            if (account != null) {
                return new RemoteUser(account.getPrincipalName(), account.getPassword(), "", account.getPersonUri());
            }
        }
        return null;
    }

    // TODO Cache
    @Override
    public ClientDetails findClient(String clientName) {

        IRemoteUser remoteUser = this.findUser(clientName);
        if (remoteUser != null) {
            //                List<String> authorizedGrantTypes = Arrays.asList("password", "refresh_token", "client_credentials");
            List<String> authorizedGrantTypes = Arrays.asList("password", "refresh_token");
            BaseClientDetails clientDetails = new BaseClientDetails();
            // in our case username <=> clientId and clientSecret <=> password
            clientDetails.setClientId(remoteUser.getUserName());
            clientDetails.setClientSecret(remoteUser.getPassword());
            clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);

            clientDetails.addAdditionalInformation("personUri", remoteUser.getPersonURI());

            return clientDetails;
        }

        LOG.warn("No client with requested clientName: " + clientName);
        throw new NoSuchClientException("No client with requested clientName: " + clientName);
    }

    /**
     *
     * @param password
     * @return
     */
    public static String passwordViaMD5(String password) {
        if (wrongMD5) {
            return BullShitPasswordEncoder.wrongMD5(password);
        } else {
            return DigestUtils.md5Hex(password);
        }
    }



}
