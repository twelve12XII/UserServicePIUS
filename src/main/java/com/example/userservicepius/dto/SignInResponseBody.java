package com.example.userservicepius.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignInResponseBody {
    long userId;
    String passwordUsernameEncoded;
}
