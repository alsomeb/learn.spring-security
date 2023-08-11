package com.example.learn.springsecurity.daos;

import com.example.learn.springsecurity.daos.impl.UserDaoImpl;
import com.example.learn.springsecurity.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

// entry point for all assertThat methods and utility methods (e.g. entry)
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


// Writing a Unit test here instead of IT
// Mocking with Mockito
// I want to make sure correct SQL is being fired

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @Mock
    private JdbcTemplate template;

    @InjectMocks
    private UserDaoImpl underTest;


    @Test
    public void testThatUserIsCreatedGeneratesCorrectSQL() {
        String expectedSQL = """
                INSERT INTO users (username, email)
                VALUES (?, ?);
                """;

        User user = User.builder()
                .id(1)
                .username("tester")
                .email("tester@tester.com")
                .build();

        underTest.addUser(user);

        // Verify template is querying expectedSQL and correct params
        verify(template).update(
                eq(expectedSQL),
                eq(user.getUsername()),
                eq(user.getEmail()));
    }

    @Test
    public void testThatUserIsInserted() {
        String sql = """
                INSERT INTO users (username, email)
                VALUES (?, ?);
                """;

        User user = User.builder()
                .username("tester")
                .email("tester@tester.com")
                .build();

        when(template.update(sql, user.getUsername(), user.getEmail()))
                .thenReturn(1);

        boolean isInserted = underTest.addUser(user);

        // Assert isInserted is true
        assertThat(isInserted).isTrue();
    }

    @Test
    public void testThatListAllUsersGeneratesCorrectSQL() {
        String expectedSQL = "SELECT id, username, email from users";
        underTest.listUsers();
        verify(template).query(eq(expectedSQL), any(RowMapper.class));
    }

    @Test
    public void testThatfindUserByIdGeneratesCorrectSQL() {
        String sql = "SELECT id, username, email FROM users WHERE id = ?";
        int id = 5;
        underTest.findUserById(id);
        verify(template).query(
                eq(sql),
                any(RowMapper.class),
                eq(id)
        );
    }
}