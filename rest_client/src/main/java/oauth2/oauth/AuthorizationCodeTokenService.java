package oauth2.oauth;

import oauth2.entity.ClientInfo;
import oauth2.entity.OAuth2Token;
import oauth2.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Curise
 * @create 2019/9/17
 */
@Service
public class AuthorizationCodeTokenService {
    @Autowired
    private AuthorizationCodeConfiguration configuration;
    @Autowired
    private ClientInfo clientInfo;

    public String getAuthorizationEndpoint() {

        Map<String, String> authParameters = new HashMap<>();
        authParameters.put("client_id", clientInfo.getClientId());
        authParameters.put("response_type", clientInfo.getResponseType());
        authParameters.put("redirect_uri",
                getEncodedUrl(clientInfo.getRedirectUri()));
        authParameters.put("scope", clientInfo.getScope());
        authParameters.put("state", clientInfo.getState());
        return buildUrl(authParameters);
    }

    private String buildUrl(Map<String, String> parameters) {
        List<String> paramList = new ArrayList<>(parameters.size());

        parameters.forEach((name, value) -> {
            paramList.add(name + "=" + value);
        });

        return clientInfo.getAuthorizeUri() + "?" + paramList.stream()
                .reduce((a, b) -> a + "&" + b).get();
    }

    private String getEncodedUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public OAuth2Token getToken(String authorizationCode) {
        RestTemplate rest = new RestTemplate();
        String authBase64 = configuration.encodeCredentials(clientInfo.getClientId(),
                clientInfo.getSecret());

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                configuration.getBody(authorizationCode, clientInfo.getRedirectUri()),
                configuration.getHeader(authBase64), HttpMethod.POST,
                URI.create(clientInfo.getTokenUri()));

        ResponseEntity<OAuth2Token> responseEntity = rest.exchange(
                requestEntity, OAuth2Token.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        throw new RuntimeException("error trying to retrieve access token");
    }

    public UserInfo tryToGetUserInfo(String token) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = clientInfo.getUserInfoUri();

        try {
            RequestEntity<Object> request = new RequestEntity<>(
                    headers, HttpMethod.GET, URI.create(endpoint));

            ResponseEntity<UserInfo> userInfo = restTemplate.exchange(request, UserInfo.class);

            if (userInfo.getStatusCode().is2xxSuccessful()) {
                return userInfo.getBody();
            } else {
                throw new RuntimeException("it was not possible to retrieve user profile");
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }

}
