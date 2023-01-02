package monitoring.dto.response;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberDTO implements Serializable {
    static final long serialVersionUID = 1991L;

    private String id;
    private String alias;
    private String phoneNumber;
}
