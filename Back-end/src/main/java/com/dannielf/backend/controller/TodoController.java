package com.dannielf.backend.controller;

import com.dannielf.backend.entity.Todo;
import com.dannielf.backend.service.TodoService;
import com.dannielf.backend.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

//@ApiResponses(value = {
//        @ApiResponse(responseCode = "201", description = "Todo saved",
//                content = {@Content(mediaType = "application/json")}),
//        @ApiResponse(responseCode = "200", description = "Found all the todos",
//                content = {@Content(mediaType = "application/json",
//                        schema = @Schema(implementation = Todo.class))}),
//        @ApiResponse(responseCode = "400", description = "Invalid getting the list",
//                content = @Content),
//        @ApiResponse(responseCode = "404", description = "Todos not found",
//                content = @Content)
//})
@CrossOrigin(origins = "*")
@RequestMapping(path = "/todo")
@RestControllerAdvice
public class TodoController {

    private final TodoService service;
    private final Response response = new Response();
    private HttpStatus httpStatus = HttpStatus.OK;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @Operation(summary = "List all todos")
    @ApiResponse(responseCode = "200",
            description = "Found all the todos",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Todo.class))})
    @GetMapping()
    public ResponseEntity<Response> list() {
        response.restart();
        try {
            response.data = service.list();
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            getErrorMessageInternal(e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new todo")
    @ApiResponse(responseCode = "201", description = "Save a todo",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Todo.class))})
    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Response> save(@RequestBody Todo todo) {
        response.restart();
        try {
            response.data = service.save(todo);
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            getErrorMessageInternal(e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a todo")
    @CrossOrigin
    @PutMapping(path = "/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") Long id, @RequestBody Todo todo) {
        response.restart();
        try {
            response.data = service.update(id, todo);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            getErrorMessageInternal(e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the todo's complete property")
    @CrossOrigin
    @PatchMapping(path = "/completed/{id}")
    public ResponseEntity<Response> updateCompleted(@PathVariable("id") Long id) {
        response.restart();
        try {
            response.data = service.updateCompleted(id);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            getErrorMessageInternal(e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete one todo")
    @CrossOrigin
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        response.restart();
        try {
            response.data = service.delete(id);

            if (response.data == null) {
                response.message = "El Todo no existe";
                httpStatus = HttpStatus.NOT_FOUND;
            } else {
                response.message = "El Todo fue removido exitosamente";
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get one todo by its id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Response> get(@PathVariable("id") Long id) {
        response.restart();
        try {
            response.data = service.get(id);
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Administrador para las excepciones del sistema
     *
     * @param exception Objeto Exception
     * @author Julian Lasso <julian.lasso@sofka.com.co>
     * @since 1.0.0
     */
    private void getErrorMessageInternal(Exception exception) {
        response.error = true;
        response.message = exception.getMessage();
        response.data = exception.getCause();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}