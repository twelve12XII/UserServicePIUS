package com.example.userservicepius.security;

import com.example.userservicepius.entity.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class AuthComponent {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public AuthComponent(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public long authCheck(String header){
        String usernameAndPasswordEncoded = header.substring("basic ".length());
        String usernameAndPassword = new String(Base64.getDecoder().decode(usernameAndPasswordEncoded));
        String[] usernameAndPasswordSplit = usernameAndPassword.split(":");
        String userName = usernameAndPasswordSplit[0];
        String password = usernameAndPasswordSplit[1];

        com.example.userservicepius.entity.User user = userRepository
                .findByName(userName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            return user.getId();
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
