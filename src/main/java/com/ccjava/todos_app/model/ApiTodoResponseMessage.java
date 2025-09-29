package com.ccjava.todos_app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiTodoResponseMessage {

    private String message;
    private Todo todo;

}
