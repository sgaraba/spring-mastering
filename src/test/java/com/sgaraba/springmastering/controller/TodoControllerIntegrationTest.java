package com.sgaraba.springmastering.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgaraba.springmastering.SpringMasteringApplication;
import com.sgaraba.springmastering.bean.Todo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringMasteringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

    private JacksonTester<Object> json;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void retrieveTodos() throws IOException {
        final ResponseEntity<String> response = template
                .getForEntity(createUrl("/users/Jack/todos"), String.class);

        List<Todo> todos = Stream.of(
                new Todo(1, "Jack", "Learn Spring MVC", LocalDate.now(), false),
                new Todo(2, "Jack", "Learn Struts", LocalDate.now(), false)
        ).collect(Collectors.toList());

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(json.write(todos).getJson());
    }

    @Test
    void retrieveTodo() throws Exception {
        final Todo mockTodo = new Todo(1, "Jack", "Learn Spring MVC", LocalDate.now(), false);

        final ResponseEntity<String> response = template
                .getForEntity(createUrl("/users/Jack/todos/1"), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        JSONAssert.assertEquals(json.write(mockTodo).getJson(), response.getBody(), false);
    }

    @Test
    public void addTodo(){
        Todo todo = new Todo(-1, "Jill", "Learn Hibernate", LocalDate.now(), false);
        URI location = template.postForLocation(createUrl("/users/Jill/todos"), todo);
        Assert.assertThat(location.getPath(), containsString("/users/Jill/todos/4"));
    }

    private String createUrl(String uri) {
        return String.format("http://localhost:%s%s" , port , uri);
    }
}