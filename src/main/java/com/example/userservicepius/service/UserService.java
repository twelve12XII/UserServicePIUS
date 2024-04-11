package com.example.userservicepius.service;


import com.example.userservicepius.dto.RegistrationDto;
import com.example.userservicepius.dto.SignInResponseBody;
import com.example.userservicepius.entity.User;
import com.example.userservicepius.entity.UserRepository;
import com.example.userservicepius.security.AuthComponent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthComponent authComponent;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthComponent authComponent) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authComponent = authComponent;
    }

    public void registration(RegistrationDto body){
        User newUser = new User(body.getName(), passwordEncoder.encode(body.getPassword()));
        userRepository.save(newUser);
    }


    public SignInResponseBody sign_in(HttpServletRequest request){
        long userId = authComponent.authCheck(request.getHeader("Authorization"));
        return new SignInResponseBody(userId, request.getHeader("Authorization"));
    }
}
