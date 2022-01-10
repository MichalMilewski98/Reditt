package reditt.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws KeyStoreException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/reditt.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new KeyStoreException("Exception");
        }
    }

    public String generateToken(Authentication authetication) throws Exception {
        User principal = (User) authetication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(this.getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() throws Exception {
        try {
            return (PrivateKey) keyStore.getKey("reditt", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new Exception("Exception occured while ");
        }
    }
}
