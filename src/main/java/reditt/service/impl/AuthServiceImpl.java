package reditt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;
import reditt.exception.RedittException;
import reditt.model.User;
import reditt.model.VerificationToken;
import reditt.repository.UserRepository;
import reditt.repository.VerificationTokenRepository;
import reditt.service.AuthService;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender javaMailSender;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
        VerificationTokenRepository verificationTokenRepository, JavaMailSender javaMailSender,
        AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setActive(false);
        this.userRepository.save(user);
        String token = this.generateVerificationToken(user);

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("reditt@email.com");
        emailMessage.setTo(registerRequest.getEmail());
        emailMessage.setSubject("Reditt registration confirmation");
        emailMessage.setText("http://localhost:8080/api/auth/accountVerifivation" + token);

        this.javaMailSender.send(emailMessage);

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        this.verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken =
                this.verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new RedittException("Invalid token"));
        this.fetchAndActivateUser(verificationToken.get());
    }

    public Authentication login(LoginRequest loginRequest) throws RedittException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        boolean isAuthenticated = this.isAuthenticated(authentication);
        if (isAuthenticated) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return authentication;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    @Transactional
    private void fetchAndActivateUser(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setActive(true);
        this.userRepository.save(user);
    }
}
