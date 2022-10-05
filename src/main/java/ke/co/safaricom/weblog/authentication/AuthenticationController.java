package ke.co.safaricom.weblog.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import ke.co.safaricom.weblog.config.WebSecurityConfig;
import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.repository.UserRepository;
import ke.co.safaricom.weblog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.refreshToken.secret}")
    private String refreshTokenSecret;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        var user = this.userRepository.findByUsername(loginRequest.getUsername());
        if (user != null) {
            if (WebSecurityConfig.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
                var token = this.generateAccessToken(user.getUsername());
                String refreshToken = generateToken(refreshTokenSecret, user.getUsername(), Date.from(Instant.now().plusSeconds(86_400)));
                return ResponseEntity.ok(Map.of("accessToken", token, "refreshToken", refreshToken));
            }
        }
        return ResponseEntity.status(401).build();
    }

    private String generateToken(String secret, String username, Date expirationTime) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        var refreshToken = Jwts.builder().setSubject(username)
                .setExpiration(expirationTime).signWith(key).compact();
        return refreshToken;
    }

    @RequestMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            var jwt = Jwts.parserBuilder()
                    .setSigningKey(refreshTokenSecret.getBytes()).build().parseClaimsJws(refreshTokenRequest.getToken());
            var accessToken = this.generateAccessToken(jwt.getBody().getSubject());
            String refreshToken = generateToken(refreshTokenSecret, jwt.getBody().getSubject(), Date.from(Instant.now().plusSeconds(86_400)));
            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }
    private String generateAccessToken(String username) {
        var expirationTime= Date.from( Instant.now().plusSeconds(3600));
        String token = generateToken(jwtSecret, username, expirationTime);
        return token;
    }

}
