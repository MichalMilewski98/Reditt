package reditt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reditt.model.RefreshToken;
import reditt.repository.RefreshTokenRepository;
import reditt.service.RefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token) {
        this.refreshTokenRepository.findByToken(token);
                //.orElseThrow(() -> new RedittException("Invalid refresh Token"));
    }

    @Override
    public void deleteRefreshToken(String token) {
        this.refreshTokenRepository.deleteByToken(token);
    }
}
