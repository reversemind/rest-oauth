package ru.ttk.baloo.rest.services;


import static junit.framework.Assert.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.ttk.baloo.rest.security.oauth.Logout;
import ru.ttk.baloo.rest.security.oauth.SampleUser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 *
 */
public class TestSimple {

    private final static Logger LOG = LoggerFactory.getLogger(TestSimple.class);


    public String getAccessToken() throws IOException {
        final String url = "http://localhost:8080/rest-oauth/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // Use Chrome plugin - Advanced Rest Client
        // username=user1&password=user1&client_id=client1&client_secret=client1&grant_type=password
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("username", SampleUser.USERNAME);
        valueMap.add("password", SampleUser.PASSWORD);

        valueMap.add("client_id", SampleUser.USERNAME);
        valueMap.add("client_secret", SampleUser.PASSWORD);

        valueMap.add("grant_type", "password");

        String response = restTemplate.postForObject(url, valueMap, String.class);
        System.out.println(response);

        Map<String, String> responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, String>>() {});
        LOG.info("responseMap:" + responseMap);

        final String key_access_token = "access_token";
        String access_token = responseMap.get(key_access_token);
        assertNotNull(access_token);

        LOG.info("Access token:" + access_token);
        return access_token;
    }

    @Test
    public void testLogout() throws IOException {

        final String url = "http://localhost:8080/rest-oauth/logout";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());


        final String accessToken = this.getAccessToken();
        assertNotNull(accessToken);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        LOG.info("Result:" + result);
        assertEquals(Logout.LOGOUT_MESSAGE, result);

    }

    @Test
    public void testGetAccessToken() throws IOException {

        final String url = "http://localhost:8080/rest-oauth/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // Use Chrome plugin - Advanced Rest Client
        // username=user1&password=user1&client_id=client1&client_secret=client1&grant_type=password
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("username", SampleUser.USERNAME);
        valueMap.add("password", SampleUser.PASSWORD);

        valueMap.add("client_id", SampleUser.USERNAME);
        valueMap.add("client_secret", SampleUser.PASSWORD);

        valueMap.add("grant_type", "password");

        String response = restTemplate.postForObject(url, valueMap, String.class);
        System.out.println(response);

        Map<String, String> responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, String>>() {
        });
        LOG.info("responseMap:" + responseMap);

        final String key_access_token = "access_token";
        String access_token = responseMap.get(key_access_token);
        assertNotNull(access_token);

        System.out.println(access_token);

        /*

         * About to connect() to localhost port 8080 (#0)
         *  Trying 127.0.0.1... connected
        > POST /rest-oauth/oauth/token HTTP/1.1
        > User-Agent: curl/7.22.0 (x86_64-unknown-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.0e zlib/1.2.5 c-ares/1.7.5 libidn/1.22 libssh2/1.2.9
        > Host: localhost:8080
        > Accept: *//*
        > Content-Length: 89
                > Content-Type: application/x-www-form-urlencoded
                >
        * upload completely sent off: 89out of 89 bytes
                < HTTP/1.1 200 OK
        HTTP/1.1 200 OK
                < Server: Apache-Coyote/1.1
        Server: Apache-Coyote/1.1
                < Cache-Control: no-store
        Cache-Control: no-store
                < Pragma: no-cache
        Pragma: no-cache
                < Content-Type: application/json;charset=UTF-8
        Content-Type: application/json;charset=UTF-8
                < Transfer-Encoding: chunked
        Transfer-Encoding: chunked
                < Date: Mon, 24 Mar 2014 12:15:12 GMT
        Date: Mon, 24 Mar 2014 12:15:12 GMT

                <
        * Connection #0 to host localhost left intact
                * Closing connection #0
        {"access_token":"2366f4bb-aeeb-4ae2-b02b-1d715c9ce7c5","token_type":"bearer","refresh_token":"5184276b-4080-453a-ba3a-6cd86a9e3709","expires_in":299999}

         */

    }

}
