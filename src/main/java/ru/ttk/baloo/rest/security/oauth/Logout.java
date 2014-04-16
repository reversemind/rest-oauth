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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.InMemoryTokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import ru.ttk.baloo.remote.core.model.Account;
import ru.ttk.baloo.remote.core.repository.AccountRepository;
import ru.ttk.baloo.rest.model.User;
import ru.ttk.baloo.rest.repository.UserRepository;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.ttk.baloo.rest.security.oauth.OAuthUtils.*;

/**
 *
 */
public class Logout implements LogoutSuccessHandler {

    private final static Logger LOG = LoggerFactory.getLogger(Logout.class);

    @Inject
    UserRepository userRepository;

    @Inject
    AccountRepository accountRepository;

    private InMemoryTokenStore tokenStore;

    public InMemoryTokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(InMemoryTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        // "c01138f9-7446-4e48-bb5c-6c1e9f1ee2d6";;"c0385cb4a2c18667337ecaf4323750b1";"entity://ORGSTRUCT.DB/ru.transtk.dc.orgStruct.portableEntities.PPerson/70bff4ac-40fb-431e-ac5d-bf25450dfeec";"GaponovV@GLOBAL.TRANSTK.RU"
        Account account = accountRepository.findOne("c01138f9-7446-4e48-bb5c-6c1e9f1ee2d6");
        LOG.info("Account:" + account);

        account = accountRepository.findByPrincipalName("GaponovV@GLOBAL.TRANSTK.RU");
        LOG.info("Account:" + account);


        List<User> users = userRepository.findAll();
        LOG.info("users:" + users);

        this.removeAccess(httpServletRequest);
        httpServletResponse.getOutputStream().write(BYE_MESSAGE.getBytes());
    }

    private void removeAccess(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            String bearerAndToken = httpServletRequest.getHeader("Authorization");
            LOG.info("bearerAndToken:" + bearerAndToken);

            if (StringUtils.isNotBlank(bearerAndToken) && bearerAndToken.length() >= OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE.length()) {
                if (bearerAndToken.contains(OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE)) {
                    String extractedToken = bearerAndToken.substring(bearerAndToken.indexOf(OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE) + OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE.length()).trim();

                    if (StringUtils.isNotBlank(extractedToken) && extractedToken.length() > 0) {
                        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(extractedToken);
                        if (this.getTokenStore() != null) {
                            this.getTokenStore().removeAccessToken(oAuth2AccessToken);
                            LOG.info("Access OAuth token removed " + oAuth2AccessToken);
                        }
                    }
                }
            }
        }
    }

}
