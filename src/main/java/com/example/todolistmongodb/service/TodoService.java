package com.example.todolistmongodb.service;

import com.example.todolistmongodb.exception.TodoNotFoundException;
import com.example.todolistmongodb.model.Todo;
import com.example.todolistmongodb.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        todo.setCreatedAt(new Date());
        todo.setLastModifiedAt(new Date());
        return todoRepository.save(todo);
    }

    public Todo find(String id) throws TodoNotFoundException {
        return todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }

    public Page<Todo> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
    }

    public Todo updateTodo(String id, Todo todo) throws TodoNotFoundException {
        Todo updatedTodo = todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        updatedTodo.setLastModifiedAt(new Date());
        updatedTodo.setContent(todo.getContent());
        updatedTodo.setTitle(todo.getTitle());
        return todoRepository.save(updatedTodo);
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
