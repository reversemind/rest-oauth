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
package ru.ttk.baloo.rest.security.oauth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import ru.ttk.baloo.rest.services.IRemoteUserFinder;

import javax.inject.Inject;

@Service
public class ClientDetailsServiceAdapter implements ClientDetailsService {

    private final static Logger LOG = LoggerFactory.getLogger(ClientDetailsServiceAdapter.class);

    @Inject
    IRemoteUserFinder remoteUserFinder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {

        if (StringUtils.isNotBlank(clientId)) {

            return remoteUserFinder.findClient(clientId);
//            IRemoteUser remoteUser = remoteUserFinder.findUser(clientId);
//            if (remoteUser != null) {
//                //                List<String> authorizedGrantTypes = Arrays.asList("password", "refresh_token", "client_credentials");
//                List<String> authorizedGrantTypes = Arrays.asList("password", "refresh_token");
//                BaseClientDetails clientDetails = new BaseClientDetails();
//                // in our case username <=> clientId and clientSecret <=> password
//                clientDetails.setClientId(remoteUser.getUserName());
//                clientDetails.setClientSecret(remoteUser.getPassword());
//                clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
//                return clientDetails;
//            }
        }

        LOG.warn("No client with requested id: " + clientId);
        throw new NoSuchClientException("No client with requested id: " + clientId);
    }

}
