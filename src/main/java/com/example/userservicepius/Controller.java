package com.example.userservicepius;


import com.example.userservicepius.dto.RegistrationDto;
import com.example.userservicepius.dto.SignInResponseBody;
import com.example.userservicepius.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void registration(@RequestBody RegistrationDto body){
        userService.registration(body);
    }
    @GetMapping
    public SignInResponseBody signIn (HttpServletRequest request){
        return userService.sign_in(request);
    }
}
