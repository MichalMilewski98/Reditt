package reditt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import reditt.model.Role;
import reditt.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final String ISSUER = "Reditt";

    public JwtProvider() {

    }

    public String getUsernameFromToken(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

  /*  public String getAccessToken(Object user) {
        List<String> userRoles = new ArrayList<>();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000))
                .withIssuer(ISSUER)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

   */


    private List<String> getUserRoles(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    private List<String> getUserAuthorities(org.springframework.security.core.userdetails.User user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
