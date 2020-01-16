package com.andersenlab.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sergei Nemogai
 * created at 16.01.2020
 */

@Data
@AllArgsConstructor
public class ClientMessage {
    private String username;
    private String text;
}
