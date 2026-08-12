package com.baina.todo.service;

import java.util.List;
import com.baina.todo.dto.TodoDto;

public interface TodoService {
    List<TodoDto> getAllTodos();
    TodoDto getTodoById(Long id);
    TodoDto createTodo(TodoDto todoDto);
    TodoDto updateTodo(Long id, TodoDto todoDto);
    void deleteTodo(Long id);
}
