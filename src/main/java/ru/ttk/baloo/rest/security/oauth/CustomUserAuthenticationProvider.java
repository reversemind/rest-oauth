package ru.ttk.baloo.rest.security.oauth;

import java.util.ArrayList;
import java.util.List;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

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
public class CustomUserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal().equals(SampleUser.USERNAME) && authentication.getCredentials().equals(SampleUser.PASSWORD)) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            CustomUserPasswordAuthenticationToken auth = new CustomUserPasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);
            return auth;
        } else {
            throw new BadCredentialsException("Bad User Credentials");
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return true;
    }

}
