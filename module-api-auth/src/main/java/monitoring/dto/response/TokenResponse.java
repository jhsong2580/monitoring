package monitoring.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenResponse implements Serializable {
    static final long serialVersionUID = 1991L;

    private String accessToken;
}

