package com.ccjava.todos_app.controller;

import com.ccjava.todos_app.exceptions.TodoNotFoundException;
import com.ccjava.todos_app.model.ApiTodoResponseMessage;
import com.ccjava.todos_app.model.Todo;
import com.ccjava.todos_app.service.TodoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/todo")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/add")
    public ResponseEntity<?> addTodo(@Valid @RequestBody Todo todo){
        todo.setDate(LocalDateTime.now());
        todo.setCompleted(false);
            return new ResponseEntity<>(ApiTodoResponseMessage.builder().message("Todo saved successfully").todo(todoService.save(todo)).build(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable String id) {
            Todo todo = todoService.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo with id: " + id + " was not found"));
            return new ResponseEntity<>(ApiTodoResponseMessage.builder().message("Todo with id: " + id + " was found").todo(todo).build(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTodos(){
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodoById(@PathVariable String id, @RequestBody Todo todo){
        Todo todoById = todoService.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo with " + id + " was not found"));
        todoById.setValue(todo.getValue());
        todoById.setCompleted(todo.getCompleted());
        todoService.save(todoById);
        return new ResponseEntity<>("Todo with " + id + " updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable String id){
        todoService.deleteById(id);
        return new ResponseEntity<>(ApiTodoResponseMessage.builder().message("Todo with " + id + " deleted successfully").build(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllTodos(){
        todoService.deleteAll();
        return new ResponseEntity<>(ApiTodoResponseMessage.builder().message("All Todos deleted successfully").build(),HttpStatus.OK);
    }
}
