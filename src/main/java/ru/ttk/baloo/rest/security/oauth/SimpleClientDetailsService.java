package ru.ttk.baloo.rest.security.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

@Service
public class SimpleClientDetailsService implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {

        if (StringUtils.isNotBlank(clientId)) {
            if (clientId.equals(SampleUser.USERNAME)) {

                List<String> authorizedGrantTypes = Arrays.asList("password", "refresh_token", "client_credentials");

                BaseClientDetails clientDetails = new BaseClientDetails();
                // in our case username <=> clientId and clientSecret <=> password
                clientDetails.setClientId(SampleUser.USERNAME);
                clientDetails.setClientSecret(SampleUser.PASSWORD);
                clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
//                clientDetails.setAuthorizedGrantTypes(Arrays.asList("password", "refresh_token", "client_credentials"));

                return clientDetails;
            }
        }
        throw new NoSuchClientException("No client with requested id: " + clientId);
    }

}
