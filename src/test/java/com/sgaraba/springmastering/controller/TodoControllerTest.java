package com.sgaraba.springmastering.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgaraba.springmastering.bean.Todo;
import com.sgaraba.springmastering.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
class TodoControllerTest {

    private static final int CREATED_TODO_ID = 4;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService service;

    private JacksonTester<Object> json;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void retrieveTodos() throws Exception {
        List<Todo> mockList = Stream.of(
                new Todo(1, "Jack", "Learn Spring MVC", LocalDate.now(), false),
                new Todo(2, "Jack", "Learn Struts", LocalDate.now(), false)
        ).collect(Collectors.toList());

        when(service.retrieveTodos(anyString())).thenReturn(mockList);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/users/Jack/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String expected = "[" + "{id:1,user:Jack,desc:\"Learn Spring MVC\",done:false}" + ","
                + "{id:2,user:Jack,desc:\"Learn Struts\",done:false}" + "]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo(json.write(mockList).getJson());
    }

    @Test
    void retrieveTodo() throws Exception {
        final Todo mockTodo = new Todo(1, "Jack", "Learn Spring MVC", LocalDate.now(), false);

        when(service.retrieveTodo(anyInt())).thenReturn(mockTodo);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/users/Jack/todos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo(json.write(mockTodo).getJson());
    }

    @Test
    public void createTodo() throws Exception {
        Todo mockTodo = new Todo(CREATED_TODO_ID, "Jack", "Learn Spring MVC", null, false);

        when(service.addTodo(anyString(), anyString(), isNull(), anyBoolean())).thenReturn(mockTodo);

        mvc.perform(
                MockMvcRequestBuilders.post("/users/Jack/todos")
                        .content(json.write(mockTodo).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/users/Jack/todos/" + CREATED_TODO_ID)));
    }
}