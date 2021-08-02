package de.neuefische.backend.service;

import de.neuefische.backend.model.Status;
import de.neuefische.backend.model.Todo;
import de.neuefische.backend.repo.TodoRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TodoServiceTest {

    @Test
    public void testRepoIsEmpty() {
        // given
        TodoService todoService = new TodoService(new TodoRepository());

        // when
        List<Todo> actual = todoService.getAll();

        //then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testRepoReturnsAValue() {
        // given
        TodoRepository todoRepositoryMock = mock(TodoRepository.class);
        when(todoRepositoryMock.findAll()).thenReturn(
                List.of(
                        new Todo("1", "description", Status.OPEN)
                )
        );
        TodoService todoService = new TodoService(todoRepositoryMock);

        // when
        List<Todo> actual = todoService.getAll();

        // then
        assertFalse(actual.isEmpty());

        Todo actualFirstTodo = actual.get(0);
        assertEquals("1", actualFirstTodo.getId());
        assertEquals("description", actualFirstTodo.getDescription());
    }

    @Test
    public void testNullTodoCreation() {
        // given
        TodoService todoService = new TodoService(new TodoRepository());

        try {
            // when
            todoService.create(null);
            fail();

        } catch (IllegalArgumentException e) {
            // expected to be thrown if todo is null
        }
    }

    @Test
    public void testNullTodoCreationWithBlankDescription() {
        // given
        TodoService todoService = new TodoService(new TodoRepository());

        try {
            // when
            Todo todo = new Todo("1", "", Status.OPEN);

            todoService.create(todo);
            fail();

        } catch (IllegalArgumentException e) {
            // expected to be thrown if todo description is blank
        }
    }

    @Test
    public void testTodoWillBeCreatedWithStatusOpen() {
        // given
        TodoService todoService = new TodoService(new TodoRepository());

        // when
        Todo todo = new Todo("1", "desc", Status.IN_PROGRESS);
        Todo actual = todoService.create(todo);

        // then
        assertEquals(Status.OPEN, actual.getStatus());
    }
}
