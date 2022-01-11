package reditt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import reditt.exception.RedittException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws RedittException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/reditt.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RedittException("Couldn't obtain secret");
        }
    }

    public String generateToken(Authentication authentication) throws RedittException {
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(this.getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() throws RedittException {
        try {
            return (PrivateKey) keyStore.getKey("reditt", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedittException("Exception occurred while obtaining private key");
        }
    }

    public boolean validateToken(String jwt)  {
        parserBuilder().setSigningKey(this.getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("reditt").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RedittException("Exception occurred while retrieving public key");
        }
    }

    public String getUsernameFromJwt(String token) throws RedittException {
        Claims claims = (Claims) parserBuilder()
                .setSigningKey(this.getPublicKey())
                .build()
                .parseClaimsJws(token);
        return claims.getSubject();
    }
}
