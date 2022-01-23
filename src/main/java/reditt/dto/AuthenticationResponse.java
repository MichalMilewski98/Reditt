package reditt.dto;

import java.time.Instant;

public class AuthenticationResponse {

    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;

    public AuthenticationResponse() { }

    public AuthenticationResponse(String authenticationToken, String refreshToken, Instant expiresAt, String username) {
        this.authenticationToken = authenticationToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.username = username;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    public static class AuthenticationResponseBuilder {
        private String authenticationToken;
        private String refreshToken;
        private Instant expiresAt;
        private String username;

        AuthenticationResponseBuilder() { }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(authenticationToken, refreshToken, expiresAt, username);
        }

        public AuthenticationResponseBuilder authenticationToken(String authenticationToken) {
            this.authenticationToken = authenticationToken;
            return this;
        }

        public AuthenticationResponseBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AuthenticationResponseBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public AuthenticationResponseBuilder username(String username) {
            this.username = username;
            return this;
        }
    }
}
