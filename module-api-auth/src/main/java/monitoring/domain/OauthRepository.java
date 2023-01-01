package monitoring.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthRepository extends JpaRepository<Oauth, String> {

    Optional<Oauth> findByAuthIdAndAuthType(String authId, AuthType authType);
}
