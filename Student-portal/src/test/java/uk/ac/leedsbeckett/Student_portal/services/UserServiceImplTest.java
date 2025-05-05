package uk.ac.leedsbeckett.Student_portal.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.leedsbeckett.Student_portal.model.User;
import uk.ac.leedsbeckett.Student_portal.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void testCreateUserSuccess() {
        // Arrange
        User input = new User("alice123", "alice@example.com", "Doe", "Alice", "password");
        User saved = new User("alice123", "alice@example.com", "Doe", "Alice", "password");
        saved.setId(1L);
        when(repository.save(input)).thenReturn(saved);

        // Act
        User result = service.createUser(input);

        // Assert
        assertNotNull(result, "The saved user should not be null");
        assertEquals(1L, result.getId(), "User ID should match the saved ID");
        assertEquals("alice123", result.getUsername(), "Username should match the input");
        verify(repository, times(1)).save(input);
    }
}
