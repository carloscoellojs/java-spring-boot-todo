package com.ccjava.todos_app.model;

import lombok.*;

@Data
@Builder
public class ApiAuthAuthenticateMessage {

    private String message;
    private String token;
}
