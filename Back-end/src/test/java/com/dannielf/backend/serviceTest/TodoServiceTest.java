package com.dannielf.backend.serviceTest;

import com.dannielf.backend.entity.Todo;
import com.dannielf.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static  org.assertj.core.api.Assertions.assertThat;

/**
 * Test Data JPA Usuario
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TodoServiceTest {

    @Autowired
    TodoRepository repository;


    @Test
    void injectedComponentsAreNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    public void testSaveTodo() {
        Todo todo = new Todo(1L, "test 1", true);
        Todo todoSaved = repository.save(todo);
        assertNotNull(todoSaved);
    }

    @Test
    public void testUpdateTodo() {
        String newText = "new text";
        Todo todo = new Todo(1L, "test 1", true);
        Todo todoSaved = repository.save(todo);
        todo.setText(newText);
        Todo todoUpdated = repository.save(todo);
        assertThat(todoUpdated.getText()).isEqualTo(newText);

    }

    @Test
    public void testDeleteTodo() {
        Long id = 1L;
        Todo todo = new Todo(1L, "test 1", true);
        repository.save(todo);
        repository.deleteById(id);
        assertThat(repository.findById(id)).isNotPresent();

    }

    @Test
    public void testGetByIdTodo() {
        Todo todo = new Todo(1L, "test 1", true);
        Todo todoSaved = repository.save(todo);
        Long id = 1L;
        Optional<Todo> todoSearched = repository.findById(id);
        assertThat(todoSearched.get().getId()).isEqualTo(id);
    }

    @Test
    public void testListTodos() {
        Todo todo = new Todo(1L, "test 1", true);
        Todo todo1 = new Todo(2L, "test 2", true);

        Todo todoSaved = repository.save(todo);
        Todo todoSaved2 = repository.save(todo1);

        List<Todo> todoList = repository.findAll();
        assertThat(todoList).size().isPositive();
    }
}
