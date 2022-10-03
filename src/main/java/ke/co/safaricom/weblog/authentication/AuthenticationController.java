package ke.co.safaricom.weblog.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import ke.co.safaricom.weblog.config.WebSecurityConfig;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/auth")
public class AuthenticationController {

    @Value("${jwt.secret}")
    private  String jwtSecret;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){

      var user=  this.userRepository.findByUsername(loginRequest.getUsername());

      if(user!= null){
         if( WebSecurityConfig.passwordEncoder().matches(loginRequest.getPassword(),user.getPassword())){
             Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
           var token=  Jwts.builder().setSubject(user.getUsername()).signWith(key).compact();
           return ResponseEntity.ok(token);
         };
      }
return ResponseEntity.status(401).build();
    }

}
