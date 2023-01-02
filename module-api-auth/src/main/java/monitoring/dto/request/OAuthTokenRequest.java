package monitoring.dto.request;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OAuthTokenRequest implements Serializable {
    static final long serialVersionUID = 1991L;

    private String oauthId;
}
