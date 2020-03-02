package com.smoothstack.lms.common.security;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoothstack.lms.common.util.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CommonIdentityTenantService {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${identityprovider.url.host:localhost}")
    private String identityProviderUrlHost;

    @Value("${identityprovider.url.port:0}")
    private int identityProviderUrlPort;

    @Value("${identityprovider.url.path:/identityprovider/getuserdetails}")
    private String identityProviderUrlPath;

    @Autowired
    Environment environment;

    public UserDetails getUserDetailsByJwt(String jwt) {
        ResponseEntity<String> response = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("Proxy-Authorization", String.format("JWT %s", jwt));

            // Content negotiation -> Accept: application/json
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity headerEntity = new HttpEntity(httpHeaders);

            String url = String.format("http://%s:%s/%s",
                    identityProviderUrlHost,
                    0 == (identityProviderUrlPort) ?
                            (environment.getProperty("local.server.port"))
                            : String.valueOf(identityProviderUrlPort),
                    identityProviderUrlPath
            );

            response = restTemplate.exchange(url,
                    HttpMethod.GET, headerEntity, String.class);
        }
        catch (Exception e) { //TODO: Proper Exception try-catch
            throw new UsernameNotFoundException("Using JWT - Identity Provider failed.");
        }
        if (!response.getStatusCode().equals(HttpStatus.OK) || !response.hasBody() || response.getBody() == null)
            throw new UsernameNotFoundException("Using JWT - Identity Provider return empty response");


        try {

            Debug.println(response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(response.getBody());
            JsonNode node = mapper.readTree(parser);

            User.UserBuilder userBuilder = User.withUsername(node.get("username").asText());
            userBuilder.password("<REDACTED>");
            Set<String> authorities = new HashSet<>();
            node.get("authorities").iterator().forEachRemaining(
                    s -> authorities.add(s.get("authority").asText())
            );
            userBuilder.authorities(authorities.toArray(new String[0]));

            Debug.println(userBuilder.build().toString());
            return userBuilder.build();
        } catch (JsonProcessingException e) {
            throw new UsernameNotFoundException("Using JWT - Identity Provider return invalid response\n"+response.getBody());
        } catch (IOException e) {
            throw new UsernameNotFoundException("Using JWT - parser failed!");
        }

    }
}
