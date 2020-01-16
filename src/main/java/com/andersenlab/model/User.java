package com.andersenlab.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

@Entity(name = "users")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @Tolerate
    public User() {}
}
