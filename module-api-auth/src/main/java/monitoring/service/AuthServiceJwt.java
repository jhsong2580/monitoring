package monitoring.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import monitoring.configuration.jwt.TokenProvider;
import monitoring.domain.Member;
import monitoring.domain.MemberRepository;
import monitoring.domain.Oauth;
import monitoring.domain.OauthRepository;
import monitoring.domain.AuthType;
import monitoring.dto.request.NormalTokenRequest;
import monitoring.dto.request.OAuthTokenRequest;
import monitoring.dto.response.MemberDTO;
import monitoring.dto.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthServiceJwt implements AuthService {

    private final TokenProvider tokenProvider;
    private final OauthRepository oAuthRepository;
    private final MemberRepository memberRepository;

    @Override
    public TokenResponse login(NormalTokenRequest tokenRequest) {
        Oauth oauth = oAuthRepository.findByAuthIdAndAuthType(tokenRequest.getEmail(),
                AuthType.NORMAL)
            .orElseThrow(() -> new IllegalArgumentException("oauth not exist"));

        if (!oauth.passwordCheck(tokenRequest.getPassword())) {
            throw new IllegalArgumentException("password not equal");
        }

        String token = tokenProvider.createToken(oauth.getMember().getId());

        return new TokenResponse(token);
    }

    @Override
    public TokenResponse login(OAuthTokenRequest tokenRequest) {
        Oauth oauth = oAuthRepository.findByAuthIdAndAuthType(tokenRequest.getOauthId(),
                AuthType.NORMAL)
            .orElseThrow(() -> new IllegalArgumentException("oauth not exist"));

        String token = tokenProvider.createToken(oauth.getMember().getId());

        return new TokenResponse(token);
    }

    @Override
    public MemberDTO findMemberByToken(String credentials) {
        if (!tokenProvider.validateToken(credentials)) {
            throw new IllegalArgumentException("token not valid");
        }

        String memberId = tokenProvider.getPayload(credentials);
        Member member = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new IllegalArgumentException("token's member not found")
            );

        return new MemberDTO(member.getId(), member.getAlias(), member.getPhoneNumber());
    }
}
