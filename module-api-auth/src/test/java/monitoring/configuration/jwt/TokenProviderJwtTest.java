package monitoring.configuration.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles(profiles = "jwt-test")
@EnableConfigurationProperties
@SpringBootTest(classes = TokenProviderJwt.class)
class TokenProviderJwtTest {

    @Autowired
    private TokenProvider tokenProviderJwt;

    @Test
    public void tokenCreateSuccess() {
        //given
        String uuid = UUID.randomUUID().toString();

        //when
        String token = tokenProviderJwt.createToken(uuid);

        //then
        assertThat(tokenProviderJwt.validateToken(token)).isTrue();
    }

    @Test
    public void getPayloadSuccess() {
        //given
        String uuid = UUID.randomUUID().toString();
        String token = tokenProviderJwt.createToken(uuid);

        //when
        String uuidFromToken = tokenProviderJwt.getPayload(token);

        //then
        assertThat(uuid).isEqualTo(uuidFromToken);
    }
}