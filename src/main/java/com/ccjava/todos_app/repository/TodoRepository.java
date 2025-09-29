package com.ccjava.todos_app.repository;

import com.ccjava.todos_app.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {
}
