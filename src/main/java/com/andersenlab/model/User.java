package com.andersenlab.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Getter
    @Setter
    private String username;

    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    private List<Message> messages = new ArrayList<>();
}
