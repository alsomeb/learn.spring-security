package com.example.learn.springsecurity.repos;

import com.example.learn.springsecurity.domain.User;
import com.example.learn.springsecurity.repositories.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryIT {

    private final UserRepositoryImpl userRepository;

    /*
        Using the @Autowired annotation (normally emitted),
        you're injecting the UserRepositoryImpl dependency into your test class,
        which allows you to perform CRUD operations and test the repository's functionality.
     */
    @Autowired
    public UserRepositoryIT(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testThatUserCanBeCreated() {
        User user = createUser("alex", "alex@alex.com");

        User addedUser = userRepository.addUser(user).orElse(null);
        assert addedUser != null;
        int expectedId = addedUser.getId();

        Optional<User> result = userRepository.findUserById(expectedId);

        assertThat(result)
                .isPresent();
        assertThat(expectedId)
                .isEqualTo(result.orElse(null).getId());
    }

    @Test
    public void testThatUserCanBeFoundByEmail() {
        String userEmail = "tester@gmail.com";
        User user = createUser("tester", userEmail);
        userRepository.addUser(user);

        User resultFind = userRepository.findUserByEmail(userEmail).orElse(null);
        assert resultFind != null;
        int expectedId = resultFind.getId();

        assertThat(resultFind.getId())
                .isEqualTo(expectedId);
    }

    private User createUser(String username, String email){
        return User.builder()
                .username(username)
                .email(email)
                .build();
    }

}

