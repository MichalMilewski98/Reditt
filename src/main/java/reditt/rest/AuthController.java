package reditt.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;
import reditt.exception.RedittException;
import reditt.repository.UserRepository;
import reditt.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        this.authService.signup(registerRequest);

        return new ResponseEntity<>("Successfully registered", HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        this.authService.verifyAccount(token);

        return new ResponseEntity<>("Successfully verified", HttpStatus.OK);
    }

   @PostMapping("/login")
    public Authentication login(@RequestBody LoginRequest loginRequest){
       if(!this.userRepository.findByUsername(loginRequest.getUsername()).isActive()) {
           throw new RedittException("User not activated");
       }
        return this.authService.login(loginRequest);
    }
}
