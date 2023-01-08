package monitoring.dto;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class GoogleAuthInfoDTO extends AuthInfoDTO {

    private String email;
    private String name;
    private String given_name;
    private String family_name;
    private String locale;
    private String id;
    private String accessToken;

    @Override
    void checkIdIsNull() {
        if (ObjectUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Google OAuth User Info don't have id value");
        }
    }

    @Override
    void checkAccessTokenIsNull() {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new IllegalArgumentException(
                "Google OAuth User Info don't have accessToken value");
        }
    }

    @Override
    public String getAuthUserId() {
        checkIdIsNull();
        return id;
    }

    @Override
    public String getAccessToken() {
        checkAccessTokenIsNull();
        return accessToken;
    }

    public void saveAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
