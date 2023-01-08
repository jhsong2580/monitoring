package monitoring.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import monitoring.backend.oauth.OAuthRestTemplateFactory;
import monitoring.dto.AuthInfoDTO;
import monitoring.enums.OAuthType;
import monitoring.backend.oauth.OAuthRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthRestTemplateFactory oAuthRestTemplateFactory;

    public ResponseEntity authorizationByAuthCode(String code, OAuthType oAuthType) {
        OAuthRestTemplate oAuthRestTemplate = oAuthRestTemplateFactory.get(oAuthType);

        String accessToken = oAuthRestTemplate.getAccessToken(code);
        AuthInfoDTO authInfoDTO = oAuthRestTemplate.getUserInfoByAccessToken(accessToken);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity getLoginURI(OAuthType oAuthType) {
        HttpHeaders headers = new HttpHeaders();
        OAuthRestTemplate oAuthRestTemplate = oAuthRestTemplateFactory.get(oAuthType);

        headers.setLocation(URI.create(oAuthRestTemplate.getAuthEndpointURI()));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
