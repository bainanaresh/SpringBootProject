package com.baina.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baina.todo.dto.TodoDto;
import com.baina.todo.entity.Todo;
import com.baina.todo.repository.TodoRepository;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoServiceImpl(TodoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto getTodoById(Long id) {
        Todo entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found: " + id));
        return modelMapper.map(entity, TodoDto.class);
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        Todo entity = modelMapper.map(todoDto, Todo.class);
        entity.setId(null); // ensure creation
        Todo saved = repository.save(entity);
        return modelMapper.map(saved, TodoDto.class);
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo existing = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found: " + id));
        // map non-null properties from dto to existing
        modelMapper.map(todoDto, existing);
        existing.setId(id); // ensure id unchanged
        Todo saved = repository.save(existing);
        return modelMapper.map(saved, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Todo not found: " + id);
        repository.deleteById(id);
    }
}
