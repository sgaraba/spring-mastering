package com.sgaraba.springmastering.controller;

import com.sgaraba.springmastering.bean.Todo;
import com.sgaraba.springmastering.exception.NotFoundException;
import com.sgaraba.springmastering.service.TodoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final MessageSource messageSource;

    @ApiOperation(
            value = "Retrieve all todos for a user by passing in his name",
            notes = "A list of matching todos is returned. Currently pagination is not supported.",
            response = Todo.class,
            responseContainer = "List",
            produces = "application/json"
    )
    @GetMapping("/users/{name}/todos")
    public List<Todo> retrieveTodos(@PathVariable String name) {
        return todoService.retrieveTodos(name);
    }

    @GetMapping(path = "/users/{name}/todos/{id}")
    public Todo retrieveTodo(@PathVariable String name, @PathVariable int id) {
        final Todo todo = todoService.retrieveTodo(id);
        if (Objects.isNull(todo)) {
            throw new NotFoundException("Todo Not Found.");
        }
        return todo;
        /*EntityModel<Todo> todoResource = new EntityModel<Todo>(todo);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveTodos(name));
        todoResource.add(linkTo.withRel("parent"));
        return todoResource;*/
    }

    @GetMapping(path = "/users/dummy-service")
    public Todo errorService() {
        throw new RuntimeException("Some Exception Occured");
    }

    @PostMapping("/users/{name}/todos")
    ResponseEntity<?> add(@PathVariable String name, @Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.addTodo(name, todo.getDesc(), todo.getTargetDate(), todo.isDone());
        if (createdTodo == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/welcome-internationalized")
    public String msg(@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("welcome.message", null, locale);
    }
}
