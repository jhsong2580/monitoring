package monitoring.configuration.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenProviderJwtTest {

    private TokenProvider tokenProviderJwt;

    @BeforeEach
    public void init() {
        tokenProviderJwt = new TokenProviderJwt(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
            3600000L
        );
    }

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