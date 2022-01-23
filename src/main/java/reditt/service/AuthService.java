package reditt.service;

import reditt.dto.AuthenticationResponse;
import reditt.dto.LoginRequest;
import reditt.dto.RefreshTokenRequest;
import reditt.dto.RegisterRequest;
import reditt.exception.RedittException;

public interface AuthService {

    void signup(RegisterRequest registerRequest);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest) throws RedittException;

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
