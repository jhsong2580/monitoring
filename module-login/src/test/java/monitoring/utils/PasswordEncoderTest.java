package monitoring.utils;


import static org.assertj.core.api.Assertions.assertThat;

import monitoring.configuration.WebSecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties
@SpringBootTest(classes = WebSecurityConfig.class)
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ParameterizedTest
    @ValueSource(strings = {"password", "1Passw0rd", "PaSsW0Rd!@#"})
    public void passwordEncode(String password) {
        //when
        String encodedPassword = passwordEncoder.encode(password);

        //then
        Assertions.assertAll(
            () -> assertThat(encodedPassword).isNotEqualTo(password),
            () -> assertThat(passwordEncoder.matches(password, encodedPassword)).isTrue()
        );

    }
}
