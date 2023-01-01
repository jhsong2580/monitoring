package monitoring.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    private String alias; // 닉네임
    private String phoneNumber;

    @Builder
    public Member(String alias, String password, String phoneNumber) {
        id = UUID.randomUUID().toString();
        this.alias = alias;
        this.phoneNumber = phoneNumber;
    }
}
