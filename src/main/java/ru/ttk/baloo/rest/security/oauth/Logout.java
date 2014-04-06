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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.InMemoryTokenStore;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *
 */
public class Logout implements LogoutSuccessHandler {

    private final static Logger LOG = LoggerFactory.getLogger(Logout.class);

    public final static String LOGOUT_MESSAGE = "You've logged out";

    private InMemoryTokenStore tokenStore;

    public InMemoryTokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(InMemoryTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest paramHttpServletRequest,
                                HttpServletResponse paramHttpServletResponse,
                                Authentication paramAuthentication) throws IOException,ServletException {
        removeAccess(paramHttpServletRequest);
        paramHttpServletResponse.getOutputStream().write(LOGOUT_MESSAGE.getBytes());
    }

    public void removeAccess(HttpServletRequest req) {
        String tokens = req.getHeader("Authorization");
        System.out.println(tokens);

        String value = tokens.substring(tokens.indexOf(" ")).trim();
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(value);

        tokenStore.removeAccessToken(value);
        LOG.info("Access Token Removed Successfully - " + token);

    }

}
