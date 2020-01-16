package com.andersenlab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

@Entity(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Message> messages = new ArrayList<>();
}
