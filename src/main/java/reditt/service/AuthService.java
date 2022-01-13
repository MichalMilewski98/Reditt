package reditt.service;

import reditt.dto.RegisterRequest;

public interface AuthService {

    void signup(RegisterRequest registerRequest);

    void verifyAccount(String token);

    //AuthenticationResponse login(LoginRequest loginRequest) throws RedittException;
}
