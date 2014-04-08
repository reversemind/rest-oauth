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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.OAuth2ErrorHandler;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.oauth2.provider.token.InMemoryTokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.ttk.baloo.rest.security.oauth.OAuthUtils.*;

/**
 *
 */
public class Logout implements LogoutSuccessHandler {

    private final static Logger LOG = LoggerFactory.getLogger(Logout.class);

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
