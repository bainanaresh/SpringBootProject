package com.baina.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baina.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
