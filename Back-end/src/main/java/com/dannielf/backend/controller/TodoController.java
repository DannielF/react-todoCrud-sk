package com.dannielf.backend.controller;

import com.dannielf.backend.entity.Todo;
import com.dannielf.backend.service.TodoService;
import com.dannielf.backend.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping(path = "/todo")
@RestController
public class TodoController {

    private final TodoService service;
    public TodoController(TodoService service) {
        this.service = service;
    }

    private final Response response = new Response();
    private HttpStatus httpStatus = HttpStatus.OK;


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


    @CrossOrigin
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        response.restart();
        try {
            response.data = service.delete(id);

            if (response.data == null) {
                response.message = "El contacto no existe";
                httpStatus = HttpStatus.NOT_FOUND;
            } else {
                response.message = "El contacto fue removido exitosamente";
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

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
     *
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