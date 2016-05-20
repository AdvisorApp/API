package com.advisorapp.api.service;

import com.advisorapp.api.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public AuthenticationService(@Value("${privateKey}") String key) {
        this.key = key;
    }

    public String provideToken(User user){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Optional<User> verifyToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Long userId = new Long(claimsJws.getBody().getSubject());
        return Optional.ofNullable(userService.getUser(userId));
    }

}
