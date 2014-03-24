package ru.ttk.baloo.rest.security.oauth;

import java.util.ArrayList;
import java.util.List;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

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
