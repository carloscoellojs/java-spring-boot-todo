package com.ccjava.todos_app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "todos")
@Data
@Builder
public class Todo {

    @Id
    private String id;
    @NotEmpty(message = "Todo value is required")
    @NotBlank(message = "Must have characters not blank spaces")
    @Length(min = 5, message = "Must have at least 5 characters")
    private String value;
    private LocalDateTime date;
    private Boolean completed;

}
