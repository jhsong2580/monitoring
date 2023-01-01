package monitoring.domain;

import static javax.persistence.EnumType.STRING;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OAuth {

    @Id
    @Column(name = "oauth_id")
    private String id;

    @Column(nullable = false)
    private String authId;
    private String password;
    @Enumerated(STRING)
    private OAuthType oAuthType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_oauth_member"))
    private Member member;

    @Builder
    public OAuth(String authId, String password, OAuthType oAuthType, Member member) {
        this.id = UUID.randomUUID().toString();
        this.authId = authId;
        this.password = password;
        this.oAuthType = oAuthType;
        this.member = member;
    }

    public boolean passwordCheck(String password) {
        return this.password.equals(password);
    }
}
