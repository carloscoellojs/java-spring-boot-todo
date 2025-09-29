package com.ccjava.todos_app.service;

import com.ccjava.todos_app.model.Todo;
import com.ccjava.todos_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Optional<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    public void deleteById(String id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll(){
        todoRepository.deleteAll();
    }
}
