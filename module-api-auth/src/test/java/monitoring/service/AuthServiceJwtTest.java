package monitoring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import monitoring.configuration.jwt.TokenProvider;
import monitoring.domain.Member;
import monitoring.domain.MemberRepository;
import monitoring.domain.OAuth;
import monitoring.domain.OAuthRepository;
import monitoring.domain.OAuthType;
import monitoring.dto.request.NormalTokenRequest;
import monitoring.dto.request.OAuthTokenRequest;
import monitoring.dto.response.MemberDTO;
import monitoring.dto.response.TokenResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceJwtTest {

    private AuthService authServiceJwt;

    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private OAuthRepository oAuthRepository;
    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void init() {
        authServiceJwt = new AuthServiceJwt(
            tokenProvider, oAuthRepository, memberRepository
        );
    }

    @Test
    public void loginNormalSuccess() {
        final String email = "email@test.com";
        final String password = "password";
        final String token = "JWT_TOKEN";
        //given
        Member member = Member.builder()
            .alias("member")
            .phoneNumber("010-9999-9999")
            .build();

        OAuth oauth = OAuth.builder()
            .authId(email)
            .oAuthType(OAuthType.NORMAL)
            .password(password)
            .member(member)
            .build();

        mockOAuth(oauth);
        mockCreateJwtToken(member.getId(), token);
        NormalTokenRequest normalTokenRequest = new NormalTokenRequest(email, password);

        //when
        TokenResponse tokenResponse = authServiceJwt.login(normalTokenRequest);

        //then
        assertThat(tokenResponse.getAccessToken()).isEqualTo(token);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "p assword", "cannot", "acc ess"})
    public void loginNormalFailWithInvalidPassword(String inputPassword) {
        //given
        final String email = "email@test.com";
        final String password = "password";

        //given
        Member member = Member.builder()
            .alias("member")
            .phoneNumber("010-9999-9999")
            .build();

        OAuth oauth = OAuth.builder()
            .authId(email)
            .oAuthType(OAuthType.NORMAL)
            .password(password)
            .member(member)
            .build();
        mockOAuth(oauth);
        NormalTokenRequest normalTokenRequest = new NormalTokenRequest(email, inputPassword);

        //when & then
        Assertions.assertThatThrownBy(
                () -> authServiceJwt.login(normalTokenRequest)
            ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("password not equal");
    }

    @Test
    public void loginOAuthSuccess() {
        final String oauthId = UUID.randomUUID().toString();
        final String password = "password";
        final String token = "JWT_TOKEN";
        //given
        Member member = Member.builder()
            .alias("member")
            .phoneNumber("010-9999-9999")
            .build();

        OAuth oauth = OAuth.builder()
            .authId(oauthId)
            .oAuthType(OAuthType.NORMAL)
            .password(password)
            .member(member)
            .build();

        mockOAuth(oauth);
        mockCreateJwtToken(member.getId(), token);

        OAuthTokenRequest oAuthTokenRequest = new OAuthTokenRequest(oauthId);

        //when
        TokenResponse tokenResponse = authServiceJwt.login(oAuthTokenRequest);

        //then
        assertThat(tokenResponse.getAccessToken()).isEqualTo(token);
    }

    @Test
    public void findMemberByTokenSuccess() {
        final String token = "JWT_TOKEN";
        //given
        Member member = Member.builder()
            .alias("member")
            .phoneNumber("010-9999-9999")
            .build();
        when(tokenProvider.validateToken(token)).thenReturn(true);
        when(tokenProvider.getPayload(token)).thenReturn(member.getId());
        mockMember(member);

        //when
        MemberDTO memberDTO = authServiceJwt.findMemberByToken(token);

        //then
        assertAll(
            () -> assertThat(memberDTO.getAlias()).isEqualTo("member"),
            () -> assertThat(memberDTO.getPhoneNumber()).isEqualTo("010-9999-9999")
        );
    }

    @Test
    public void findMemberByInvalidMember() {
        final String token = "JWT_TOKEN";
        //given
        Member member = Member.builder()
            .alias("member")
            .phoneNumber("010-9999-9999")
            .build();
        when(tokenProvider.validateToken(token)).thenReturn(true);
        when(tokenProvider.getPayload(token)).thenReturn("INVALID_MEMBER_ID");

        //when & then
        assertThatThrownBy(
            () -> authServiceJwt.findMemberByToken(token)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("token's member not found");

    }

    private void mockMember(Member member) {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
    }

    private void mockOAuth(OAuth oauth) {
        when(oAuthRepository.findByAuthIdAndOAuthType(oauth.getAuthId(), oauth.getOAuthType()))
            .thenReturn(Optional.of(oauth));
    }

    private void mockCreateJwtToken(String payload, String returnToken) {
        when(tokenProvider.createToken(payload)).thenReturn(returnToken);
    }
}