package reditt.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;
import reditt.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        this.authService.signup(registerRequest);

        return new ResponseEntity<>("Succesfully registered", HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        this.authService.verifyAccount(token);

        return new ResponseEntity<>("Sucessfully verified", HttpStatus.OK);
    }

   @PostMapping("/login")
    public Authentication login(@RequestBody LoginRequest loginRequest){
        return this.authService.login(loginRequest);
    }
}
