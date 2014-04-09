package ru.ttk.baloo.rest.services;

import static junit.framework.Assert.*;
import static ru.ttk.baloo.rest.security.oauth.OAuthUtils.OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.ttk.baloo.rest.dto.HouseDTO;
import ru.ttk.baloo.rest.security.oauth.Logout;
import ru.ttk.baloo.rest.security.oauth.OAuthUtils;
import ru.ttk.baloo.rest.security.oauth.SampleUser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 *
 */
public class SimpleOAuthAuthorizationIntegrationTest {

    private final static Logger LOG = LoggerFactory.getLogger(SimpleOAuthAuthorizationIntegrationTest.class);

    private Server server;

    final int PORT_NUMBER = 18181;
    final String WEB_CONTEXT_PATH = "rest-oauth";

    @Before
    public void init() throws Exception {
        // rest-oauth-0.0.1-SNAPSHOT.war
        server = new Server(PORT_NUMBER);
        server.setStopAtShutdown(true);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/" + WEB_CONTEXT_PATH);
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setClassLoader(getClass().getClassLoader());
        server.addHandler(webAppContext);

        server.start();
    }

    @After
    public void destroy() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    public void testPostJSON2REST() throws IOException, InterruptedException {
        String accessToken = "123";
        accessToken = this.authorizeAndGetAccessToken();
        assertNotNull(accessToken);

        Thread.sleep(500);


        final String url = "http://localhost:" + PORT_NUMBER + "/" + WEB_CONTEXT_PATH + "/resources/create/house";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE + accessToken);

        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setUuid("UUID");
        houseDTO.setNumber("NUMBER");

        HttpEntity<HouseDTO> request = new HttpEntity<HouseDTO>(houseDTO, headers);

        String response = restTemplate.postForObject(url, request, String.class);
        String result = response;

        LOG.info("Result:" + result);
    }

    @Test
    public void testAccessToAuthorizedMethods() throws IOException, InterruptedException {

        String accessToken = "123";
        accessToken = this.authorizeAndGetAccessToken();
        assertNotNull(accessToken);

        Thread.sleep(500);

        final String url = "http://localhost:" + PORT_NUMBER + "/" + WEB_CONTEXT_PATH + "/resources/service/create";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE + accessToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        LOG.info("Result:" + result);
    }

    /**
     *
     * >curl --noproxy localhost -v -H "Authorization:Bearer 18b90bd3-1749-4162-8b6a-f37a1f206e86"  http://localhost:18181/rest-oauth/logout
     * About to connect() to localhost port 18181 (#0)
     *   Trying 127.0.0.1... connected
     > GET /rest-oauth/logout HTTP/1.1
     > User-Agent: curl/7.22.0 (x86_64-unknown-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.0e zlib/1.2.5 c-ares/1.7.5 libidn/1.22 libssh2/1.2.9
     > Host: localhost:18181
     > Accept: *
     > Authorization:Bearer 18b90bd3-1749-4162-8b6a-f37a1f206e86
     >
     < HTTP/1.1 200 OK
     < Content-Length: 4
     < Server: Jetty(6.1.25)
     <
     * Connection #0 to host localhost left intact
     * Closing connection #0
     * Bye!
     *
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testLogout() throws IOException, InterruptedException {

        final String url = "http://localhost:" + PORT_NUMBER + "/" + WEB_CONTEXT_PATH + "/logout";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        final String accessToken = this.authorizeAndGetAccessToken();
        assertNotNull(accessToken);

        Thread.sleep(500);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE + accessToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        LOG.info("Result:" + result);
        assertEquals(OAuthUtils.BYE_MESSAGE, result);
    }

    /**
     *
     >curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=zMMeaQnMWDsDpGLh&grant_type=password" -H "Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo1" http://localhost:18181/rest-oauth/oauth/token
     * About to connect() to localhost port 18181 (#0)
     *   Trying 127.0.0.1... connected
     > POST /rest-oauth/oauth/token HTTP/1.1
     > User-Agent: curl/7.22.0 (x86_64-unknown-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.0e zlib/1.2.5 c-ares/1.7.5 libidn/1.22 libssh2/1.2.9
     > Host: localhost:18181
     > Accept: *
     > Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo1
     > Content-Length: 68
     > Content-Type: application/x-www-form-urlencoded
     >
     * upload completely sent off: 68out of 68 bytes
     < HTTP/1.1 200 OK
     HTTP/1.1 200 OK
     < Cache-Control: no-store
     Cache-Control: no-store
     < Pragma: no-cache
     Pragma: no-cache
     < Content-Type: application/json;charset=UTF-8
     Content-Type: application/json;charset=UTF-8
     < Transfer-Encoding: chunked
     Transfer-Encoding: chunked
     < Server: Jetty(6.1.25)
     Server: Jetty(6.1.25)

     <
     * Connection #0 to host localhost left intact
     * Closing connection #0
     {"access_token":"07eb1bb0-3b57-46fe-b074-28c86f7e4faf","token_type":"bearer","refresh_token":"4b59b20c-8eac-4dbb-8451-fd67eb23d6b3","expires_in":29}






     WRONG



     >curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=zMMeaQnMWDsDpGLh&grant_type=password" -H "Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo" http://localhost:18181/rest-oauth/oauth/token
     * About to connect() to localhost port 18181 (#0)
     *   Trying 127.0.0.1... connected
     > POST /rest-oauth/oauth/token HTTP/1.1
     > User-Agent: curl/7.22.0 (x86_64-unknown-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.0e zlib/1.2.5 c-ares/1.7.5 libidn/1.22 libssh2/1.2.9
     > Host: localhost:18181
     > Accept: *
     > Authorization:Basic XNhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo
     > Content-Length: 68
     > Content-Type: application/x-www-form-urlencoded
     >
     * upload completely sent off: 68out of 68 bytes
     < HTTP/1.1 401 Unauthorized
     HTTP/1.1 401 Unauthorized
     < Cache-Control: no-store
     Cache-Control: no-store
     < Pragma: no-cache
     Pragma: no-cache
     < WWW-Authenticate: Basic realm="ttkrest/client", error="unauthorized", error_description="Invalid basic authentication token"
     WWW-Authenticate: Basic realm="ttkrest/client", error="unauthorized", error_description="Invalid basic authentication token"
     < Content-Type: application/json;charset=UTF-8
     Content-Type: application/json;charset=UTF-8
     < Transfer-Encoding: chunked
     Transfer-Encoding: chunked
     < Server: Jetty(6.1.25)
     Server: Jetty(6.1.25)

     <
     * Connection #0 to host localhost left intact
     * Closing connection #0
     {"error":"unauthorized","error_description":"Invalid basic authentication token"}


     WRONG PASSWORD
     A001005029:/home/admin/VACANCY/07042014 # curl --noproxy localhost -i -X POST -v -d "username=_sample_user_&password=WRONG_PASSWORD&grant_type=password" -H "Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo" http://localhost:18181/rest-oauth/oauth/token
     * About to connect() to localhost port 18181 (#0)
     *   Trying 127.0.0.1... connected
     > POST /rest-oauth/oauth/token HTTP/1.1
     > User-Agent: curl/7.22.0 (x86_64-unknown-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.0e zlib/1.2.5 c-ares/1.7.5 libidn/1.22 libssh2/1.2.9
     > Host: localhost:18181
     > Accept: *
     > Authorization:Basic X3NhbXBsZV91c2VyXzp6TU1lYVFuTVdEc0RwR0xo
     > Content-Length: 66
     > Content-Type: application/x-www-form-urlencoded
     >
     * upload completely sent off: 66out of 66 bytes
     < HTTP/1.1 400 Bad Request
     HTTP/1.1 400 Bad Request
     < Cache-Control: no-store
     Cache-Control: no-store
     < Pragma: no-cache
     Pragma: no-cache
     < Content-Type: application/json;charset=UTF-8
     Content-Type: application/json;charset=UTF-8
     < Transfer-Encoding: chunked
     Transfer-Encoding: chunked
     < Server: Jetty(6.1.25)
     Server: Jetty(6.1.25)

     <
     * Connection #0 to host localhost left intact
     * Closing connection #0
     {"error":"invalid_grant","error_description":"Wrong user credentials"}



     *
     * @throws IOException
     */
    @Test
    public void testAuthorizationGetAccessTokenViaBasic() throws IOException {

        final String url = "http://localhost:" + PORT_NUMBER + "/" + WEB_CONTEXT_PATH + "/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        // right now username = client_id & password = client_secret
        String base64 = new String(Base64.encodeBase64((SampleUser.USERNAME + ":" + SampleUser.PASSWORD).getBytes()).clone());
        LOG.info("base64 for string: " + SampleUser.USERNAME + ":" + SampleUser.PASSWORD + " =" + base64);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + base64);


        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("grant_type", "password");
        valueMap.add("username", SampleUser.USERNAME);
        valueMap.add("password", SampleUser.PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(valueMap, headers);


        String response = restTemplate.postForObject(url, request, String.class);
        LOG.info("Response from server:" + response);

        Map<String, String> responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, String>>() {
        });
        LOG.info("responseMap:" + responseMap);

        final String key_access_token = "access_token";
        String access_token = responseMap.get(key_access_token);
        assertNotNull(access_token);

        LOG.info("access_token:" + access_token);

    }

    @Test
    public void testAuthorizationGetAccessTokenViaUrlEncoded() throws IOException {

        final String url = "http://localhost:" + PORT_NUMBER + "/" + WEB_CONTEXT_PATH + "/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        // right now username = client_id & password = client_secret

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("grant_type", "password");
        valueMap.add("username", SampleUser.USERNAME);
        valueMap.add("password", SampleUser.PASSWORD);
        valueMap.add("client_id", SampleUser.USERNAME);
        valueMap.add("client_secret", SampleUser.PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(valueMap, headers);


        String response = restTemplate.postForObject(url, request, String.class);
        LOG.info("Response from server:" + response);

        Map<String, String> responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, String>>() {
        });
        LOG.info("responseMap:" + responseMap);

        final String key_access_token = "access_token";
        String access_token = responseMap.get(key_access_token);
        assertNotNull(access_token);

        LOG.info("access_token:" + access_token);
    }

    /**
     * @return
     * @throws IOException
     */
    private String authorizeAndGetAccessToken() throws IOException {

        final String url = "http://localhost:" + PORT_NUMBER + "/" + WEB_CONTEXT_PATH + "/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        // right now username = client_id & password = client_secret
        String base64 = new String(Base64.encodeBase64((SampleUser.USERNAME + ":" + SampleUser.PASSWORD).getBytes()).clone());
        LOG.info("base64 for string: " + SampleUser.USERNAME + ":" + SampleUser.PASSWORD + " :" + base64);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + base64);


        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("grant_type", "password");
        valueMap.add("username", SampleUser.USERNAME);
        valueMap.add("password", SampleUser.PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(valueMap, headers);


        String response = restTemplate.postForObject(url, request, String.class);
        LOG.info("Response from server:" + response);

        Map<String, String> responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, String>>() {
        });
        LOG.info("responseMap:" + responseMap);

        final String key_access_token = "access_token";
        String access_token = responseMap.get(key_access_token);
        assertNotNull(access_token);

        LOG.info("Access token:" + access_token);
        return access_token;
    }
}
