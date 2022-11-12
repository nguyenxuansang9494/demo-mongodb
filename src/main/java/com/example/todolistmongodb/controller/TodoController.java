package com.example.todolistmongodb.controller;

import com.example.todolistmongodb.exception.TodoNotFoundException;
import com.example.todolistmongodb.model.Todo;
import com.example.todolistmongodb.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PutMapping("/")
    ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.createTodo(todo));
    }

    @GetMapping("/")
    ResponseEntity<Page<Todo>> getTodos(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(todoService.findAll(page, size));
    }

    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodo(@PathVariable("id") String id) throws TodoNotFoundException {
        return ResponseEntity.ok(todoService.find(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Todo> updateTodo(@PathVariable("id") String id, Todo todo) throws TodoNotFoundException {
        return ResponseEntity.ok(todoService.updateTodo(id, todo));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTodo(@PathVariable("id") String id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ TodoNotFoundException.class })
    ResponseEntity<String> handleNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo is not found.");
    }
}