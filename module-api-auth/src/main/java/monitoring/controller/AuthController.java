package monitoring.controller;

import javax.print.attribute.standard.Media;
import lombok.RequiredArgsConstructor;
import monitoring.argumentResolver.JwtToken;
import monitoring.dto.request.NormalTokenRequest;
import monitoring.dto.request.OAuthTokenRequest;
import monitoring.dto.response.MemberDTO;
import monitoring.dto.response.TokenResponse;
import monitoring.exception.CustomInvalidBodyException;
import monitoring.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/auth/normal",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity auth(
        @Validated NormalTokenRequest tokenRequest,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new CustomInvalidBodyException(bindingResult);
        }

        TokenResponse tokenResponse = authService.login(tokenRequest);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping(value = "/auth/oauth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity auth(
        @Validated OAuthTokenRequest tokenRequest,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new CustomInvalidBodyException(bindingResult);
        }

        TokenResponse tokenResponse = authService.login(tokenRequest);

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findMemberByToken (
        @JwtToken String token
    ){
        MemberDTO memberDTO = authService.findMemberByToken(token);

        return ResponseEntity.ok(memberDTO);
    }
}
