package monitoring.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthRepository extends JpaRepository<OAuth, String> {

    Optional<OAuth> findByAuthIdAndOAuthType(String authId, OAuthType oAuthType);
}
