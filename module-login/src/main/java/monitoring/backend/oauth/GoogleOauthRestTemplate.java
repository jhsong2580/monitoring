package monitoring.backend.oauth;


import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import monitoring.dto.AuthInfoDTO;
import monitoring.dto.GoogleAuthInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleOauthRestTemplate extends OAuthRestTemplate {

    private String authEndpointURI = "";
    @Value("${spring.security.oauth2.client.registration.google.auth-endpoint}")
    private String authEndpoint;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.redirect-url}")
    private String redirectURL;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String secret;

    @Value("${spring.security.oauth2.client.registration.google.token-endpoint}")
    private String tokenEndpoint;

    @Value("${spring.security.oauth2.client.registration.google.fetching_data_endpoint}")
    private String fetchingDataEndpoint;

    @PostConstruct
    public void init() {
        Map<String, List<String>> param = new HashMap<>();
        MultiValueMap<String, String> queryParams = new MultiValueMapAdapter<>(param);
        queryParams.add("client_id", clientId);
        queryParams.add("redirect_uri", redirectURL);
        queryParams.add("response_type", "code");
        queryParams.add("scope",
            "https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile");
        queryParams.add("approval_prompt", "force");
        queryParams.add("access_type", "offline");

        URI uri = UriComponentsBuilder
            .fromUriString("https://accounts.google.com/o/oauth2/auth")
            .queryParams(queryParams)
            .build()
            .toUri();
        authEndpointURI = uri.toString();
    }

    public String getAuthEndpointURI() {
        return authEndpointURI;
    }

    public String getAccessToken(String code) {
        RestTemplate restTemplate = settingRestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", secret);
        params.put("redirect_uri", redirectURL);
        params.put("code", code);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, params,
            Map.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException();
        }

        return (String) response.getBody().get("access_token");
    }

    public AuthInfoDTO getUserInfoByAccessToken(String accessToken) {
        RestTemplate restTemplate = settingRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<GoogleAuthInfoDTO> response = restTemplate.exchange(fetchingDataEndpoint,
            HttpMethod.GET, new HttpEntity<>("", httpHeaders), GoogleAuthInfoDTO.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException();
        }

        GoogleAuthInfoDTO userInfoDTO = response.getBody();
        userInfoDTO.saveAccessToken(accessToken);

        return userInfoDTO;
    }
}
