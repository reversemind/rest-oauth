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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *
 */
public class CustomUserPasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final static Logger LOG = LoggerFactory.getLogger(CustomUserPasswordAuthenticationToken.class);

    private final Object principal;
    private Object credentials;
    private int loginId;

    public int getLoginId() {
        return loginId;
    }

    public CustomUserPasswordAuthenticationToken(Object principal,
                                                 Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public CustomUserPasswordAuthenticationToken(Object principal,
                                                 Object credentials,
                                                 Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public CustomUserPasswordAuthenticationToken(Object principal,
                                                 Object credentials,
                                                 Collection<? extends GrantedAuthority> authorities,
                                                 String personUri) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}