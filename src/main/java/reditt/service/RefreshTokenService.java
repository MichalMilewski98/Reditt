package reditt.service;

import reditt.model.RefreshToken;

public interface RefreshTokenService {

    void deleteRefreshToken(String token);

    void validateRefreshToken(String token);

    RefreshToken generateRefreshToken();
}
