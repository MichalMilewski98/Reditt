package reditt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reditt.dto.RegisterRequest;
import reditt.exception.RedittException;
import reditt.model.User;
import reditt.model.VerificationToken;
import reditt.repository.UserRepository;
import reditt.repository.VerificationTokenRepository;
import reditt.service.AuthService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    //private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
        VerificationTokenRepository verificationTokenRepository,
        AuthenticationManager authenticationManager, JavaMailSender javaMailSender) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
       // this.authenticationManager = authenticationManager;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {

        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
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

    /*public AuthenticationResponse login(LoginRequest loginRequest) throws RedittException {
        Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new AuthenticationResponse(token, loginRequest.getUsername());
    }

     */

    @Transactional
    private void fetchAndActivateUser(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setActive(true);
        this.userRepository.save(user);
    }
}
