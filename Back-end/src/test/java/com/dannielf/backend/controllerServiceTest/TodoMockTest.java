package com.dannielf.backend.controllerServiceTest;

import com.dannielf.backend.repository.TodoRepository;
import com.dannielf.backend.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Mock UsuarioRol
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootTest
public class TodoMockTest {

    @MockBean
    TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @Test
    public void testTodoMock() {
        when(todoRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(todoService.list()).isEmpty();
        verify(todoRepository).findAll();
    }
}
