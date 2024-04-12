package com.example.userservicepius.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "pius_project")
public class User {
    @Id
    @GeneratedValue
    long id;
    @Column(unique = true)
    String name;
    String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}