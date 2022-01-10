package reditt.service;

import reditt.dto.AuthenticationResponse;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;

public interface AuthService {

    void signup(RegisterRequest registerRequest);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);
}
