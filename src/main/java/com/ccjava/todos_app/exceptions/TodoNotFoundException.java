package com.ccjava.todos_app.exceptions;

public class TodoNotFoundException  extends RuntimeException {
    public TodoNotFoundException(String message) {
        super(message);
    }
}
