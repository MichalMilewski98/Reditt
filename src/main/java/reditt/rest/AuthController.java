package reditt.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reditt.dto.LoginRequest;
import reditt.dto.RegisterRequest;
import reditt.exception.RedittException;
import reditt.repository.UserRepository;
import reditt.security.JwtProvider;
import reditt.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
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

   /* @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                String username = this.jwtProvider.getUsernameFromToken(authorizationHeader);
                User user = userRepository.findByUsername(username);
                String accessToken = this.jwtProvider.getAccessToken(user);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getLocalizedMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token missing");
        }
    }

    */
}
