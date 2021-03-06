package com.andersenlab.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Sergei Nemogai
 * created on 14.01.2020
 */

@Entity(name = "messages")
@Data
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @Column(name = "send_at", nullable = false)
    private Timestamp sendAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Tolerate
    public Message() {}
}
