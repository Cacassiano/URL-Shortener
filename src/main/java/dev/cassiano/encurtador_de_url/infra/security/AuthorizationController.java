package dev.cassiano.encurtador_de_url.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.encurtador_de_url.domain.user.entity.User;
import dev.cassiano.encurtador_de_url.domain.user.repository.UserRepository;
import dev.cassiano.encurtador_de_url.infra.security.dto.TokenDTO;
import dev.cassiano.encurtador_de_url.infra.security.dto.UserDTO;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {
    
    @Autowired
    private TokenService service;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> registerNewUser(@RequestBody UserDTO user) {
        if (user.username().length() > 1 && 
            user.email().length() > 1 && 
            user.password().length() > 1 && 
            repository.findByEmail(user.email()).isEmpty()) {
            
            User nUser = new User();
            nUser.setEmail(user.email());
            nUser.setPassword(encoder.encode(user.password()));
            nUser.setUsername(user.username());
            
            repository.save(nUser);
            TokenDTO tokenDTO = new TokenDTO(nUser.getEmail(),service.generateToken(nUser.getEmail()));
            return ResponseEntity.ok(tokenDTO);
        } 
        throw new NullPointerException("Required information is invalid");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> createToken(@RequestBody UserDTO user) {
        User myUser = repository.findByEmail(user.email()).orElseThrow(() -> new NullPointerException("User not found"));
        if (!encoder.matches(user.password(), myUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        } 
        String token = service.generateToken(user.email());
        return ResponseEntity.ok(new TokenDTO(user.email(), token));
    }
}
