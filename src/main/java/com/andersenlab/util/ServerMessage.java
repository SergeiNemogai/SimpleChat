package com.andersenlab.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Sergei Nemogai
 * created at 16.01.2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerMessage {
    String username;
    List<ClientMessage> messages;
}
