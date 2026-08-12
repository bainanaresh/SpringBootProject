package com.baina.todo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.baina.todo.dto.TodoDto;
import com.baina.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Test
    void list_shouldReturnAllTodos() throws Exception {
        TodoDto todo1 = new TodoDto();
        todo1.setId(1L);
        todo1.setTitle("Learn Java");
        todo1.setDescription("Practice OOP");
        todo1.setCompleted(false);

        TodoDto todo2 = new TodoDto();
        todo2.setId(2L);
        todo2.setTitle("Build API");
        todo2.setDescription("Create Spring Boot app");
        todo2.setCompleted(true);

        when(todoService.getAllTodos()).thenReturn(List.of(todo1, todo2));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Learn Java"))
                .andExpect(jsonPath("$[1].title").value("Build API"));
    }

    @Test
    void getById_shouldReturnTodo() throws Exception {
        TodoDto todo = new TodoDto();
        todo.setId(1L);
        todo.setTitle("Write tests");
        todo.setDescription("JUnit 5");
        todo.setCompleted(false);

        when(todoService.getTodoById(1L)).thenReturn(todo);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Write tests"));
    }

    @Test
    void create_shouldCreateTodoAndReturnCreatedStatus() throws Exception {
        TodoDto request = new TodoDto();
        request.setTitle("New Task");
        request.setDescription("Using MockMvc");
        request.setCompleted(false);

        TodoDto created = new TodoDto();
        created.setId(10L);
        created.setTitle("New Task");
        created.setDescription("Using MockMvc");
        created.setCompleted(false);

        when(todoService.createTodo(any(TodoDto.class))).thenReturn(created);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/todos/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void update_shouldReturnUpdatedTodo() throws Exception {
        TodoDto request = new TodoDto();
        request.setTitle("Updated Task");
        request.setDescription("Updated description");
        request.setCompleted(true);

        TodoDto updated = new TodoDto();
        updated.setId(1L);
        updated.setTitle("Updated Task");
        updated.setDescription("Updated description");
        updated.setCompleted(true);

        when(todoService.updateTodo(eq(1L), any(TodoDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(todoService).deleteTodo(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }
}
