package reditt.service;

import org.springframework.security.core.Authentication;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;
import reditt.exception.RedittException;

public interface AuthService {

    void signup(RegisterRequest registerRequest);

    void verifyAccount(String token);

    Authentication login(LoginRequest loginRequest) throws RedittException;
}
