package com.andersenlab.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

@Entity(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String text;

    @Column(name = "send_at", nullable = false)
    @Getter
    @Setter
    private Timestamp sendAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;
}
