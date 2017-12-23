package com.spring.bootexample.controller;

import com.spring.bootexample.common.ExceptionResponse;
import com.spring.bootexample.common.TodoNotFoundException;
import com.spring.bootexample.model.Todo;
import com.spring.bootexample.service.TodoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(
            value = "Retrieve all todos for a user by passing in his name",
            notes = "A list of matching todos is returned. Current pagination is not supported.",
            response = Todo.class,
            responseContainer = "List",
            produces = "application/json")
    @GetMapping("/users/{name}/todos")
    public List<Todo> retrieveTodos(@PathVariable String name) {
        return todoService.retrieveTodos(name);
    }

    @GetMapping("/users/{name}/todos/{id}")
    public Resource<Todo> retrieveTodo(@PathVariable String name, @PathVariable int id) {
        Todo todo = todoService.retrieveTodo(id);
        if (todo == null) {
            throw new TodoNotFoundException("Todo Not Found");
        }
        Resource<Todo> todoResource = new Resource<Todo>(todo);
        ControllerLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveTodos(name));
        todoResource.add(linkTo.withRel("parent"));

        return todoResource;
    }

    @PostMapping("/users/{name}/todos")
    ResponseEntity<?> add(@PathVariable String name,  @Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.addTodo(name, todo.getDesc(), todo.getTargetDate(), todo.isDone());
        if (createdTodo == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users/dummy-service")
    public Todo errorService() {
        throw new RuntimeException("Some Exception Occurred");
    }


    @GetMapping("/welcome-internationalized")
    public String msg(@RequestHeader(value = "Accept-Language",
            required = false) Locale locale) {
        return messageSource.getMessage("welcome.message", null,
                locale);
    }
}
