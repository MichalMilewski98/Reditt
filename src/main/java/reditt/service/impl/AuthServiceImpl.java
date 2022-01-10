package reditt.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reditt.dto.AuthenticationResponse;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;
import reditt.model.User;
import reditt.model.VerificationToken;
import reditt.repository.UserRepository;
import reditt.repository.VerificationTokenRepository;
import reditt.security.JwtProvider;
import reditt.service.AuthService;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
        VerificationTokenRepository verificationTokenRepository, AuthenticationManager authenticationManager,
    JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setActive(false);

        userRepository.save(user);
    }

    public void generateVerificationToken(User user) {
        String verificationToken = UUID.randomUUID().toString();
        VerificationToken token = verificationToken;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findAllById();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception {
        Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
