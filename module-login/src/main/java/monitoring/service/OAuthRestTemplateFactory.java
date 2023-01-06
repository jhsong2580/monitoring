package monitoring.service;

import lombok.RequiredArgsConstructor;
import monitoring.enums.OAuthType;
import monitoring.service.oauth.GoogleOauthRestTemplate;
import monitoring.service.oauth.OAuthRestTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthRestTemplateFactory {

    private final GoogleOauthRestTemplate googleOauthRestTemplate;

    public OAuthRestTemplate get(OAuthType oAuthType){
        if(oAuthType == OAuthType.GOOGLE){
            return googleOauthRestTemplate;
        }
        throw new IllegalArgumentException("no oauth rest template of " + oAuthType.name());
    }

}
