package ru.ttk.baloo.rest.security.oauth;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

/**
 *
 */
public class OAuthUtils extends OAuth2Utils {

    public final static String OAUTH_HEADER_VALUE_BEARER = DefaultOAuth2AccessToken.BEARER_TYPE;
    public final static String OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE = DefaultOAuth2AccessToken.BEARER_TYPE + " ";


    public final static String HTTP_200 = "HTTP/1.1 200 OK\n";
    public final static String BYE_MESSAGE = "Bye!";
    public final static String HTTP_200_BYE_MESSAGE = HTTP_200 + "\n" + BYE_MESSAGE;
    public final static String WRONG_USER_CREDENTIALS = "Wrong user credentials";

}
