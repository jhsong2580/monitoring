package monitoring.service;

import monitoring.dto.request.NormalTokenRequest;
import monitoring.dto.request.OAuthTokenRequest;
import monitoring.dto.response.MemberDTO;
import monitoring.dto.response.TokenResponse;

public interface AuthService {

    TokenResponse login(NormalTokenRequest tokenRequest);

    TokenResponse login(OAuthTokenRequest tokenRequest);

    MemberDTO findMemberByToken(String credentials);
}
