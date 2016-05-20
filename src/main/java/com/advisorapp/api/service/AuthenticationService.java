package com.advisorapp.api.service;

import com.advisorapp.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by damien on 20/05/2016.
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    private String key;

    private String salt;

    /**
     * Default value for JWT key
     * BAD ! Used only for tests.
     * TODO : rethink that
     */
    public static final String defaultKey = "myPrivateForTest";
    public static final String defaultSalt = "$2a$10$Bb1GjJo8hZM3cnW1kqDZlu";

    @Autowired
    public AuthenticationService(@Value("${privateKey:" + AuthenticationService.defaultKey + "}") String key, @Value("${salt:" + AuthenticationService.defaultSalt + "}") String salt) {
        this.key = key;
        this.salt = salt;
    }

    public String provideToken(User user){
        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Optional<User> verifyToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Long userId = new Long(claimsJws.getBody().getSubject());
        return Optional.ofNullable(userService.getUser(userId));
    }

    public String hashPassword(String givenPassword){
        return BCrypt.hashpw(givenPassword, salt);
    }

}
