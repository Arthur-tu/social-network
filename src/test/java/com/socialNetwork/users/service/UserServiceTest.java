package com.socialNetwork.users.service;

import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.repository.UserRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class UserServiceTest {

    @Test
    void createUserSuccess() {
        //given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
       
        User user = new User("Testname", "Testsurname", "Male", "Russia","79261134321", 
                date,"Testtext", "testuser@test.com", "123", "testurl", 
                false, "Moscow");
        User savedUser = new User(1, "Testname", "Testsurname", "Male", "Russia",
                "79261134321", date,"Testtext", "testuser@test.com", "123",
                "testurl", false, "Moscow");
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository);
        //when
        String result = userService.createUser(user);
        //then
        Assertions.assertEquals("Пользователь Testname добавлен с id = 1", result);
    }

    @Test
    void createUserError() {
        //given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = new User("Testname", "Testsurname", "Male", "Russia","79261134321",
                date,"Testtext", "testuser@test.com", "123", "testurl",
                false, "Moscow");
        Mockito.when(userRepository.save(user)).thenThrow(PersistenceException.class);
        UserService userService = new UserService(userRepository);
        //when
        Executable executable = () -> userService.createUser(user);
        //then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    @Test
    void getUserSuccess() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        int userId = 1;
        User user = new User(userId, "Testname", "Testsurname", "Male", "Russia",
                "79261134321", new Date(), "Testtext", "testuser@test.com", "123",
                "testurl", false, "Moscow");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserService userService = new UserService(userRepository);

        User result = userService.getUser(userId);

        Assertions.assertEquals("Testname", result.getName());
    }

    @Test
    void getUserNotFound() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        int userId = 1;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        UserService userService = new UserService(userRepository);

        Executable executable = () -> userService.getUser(userId);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void updateUserSuccess() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        int userId = 1;
        User user = new User(userId, "Testname", "Testsurname", "Male", "Russia",
                "79261134321", new Date(), "Testtext", "testuser@test.com", "123",
                "testurl", false, "Moscow");
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserService userService = new UserService(userRepository);

        String result = userService.updateUser(user, userId);

        Assertions.assertEquals("Пользователь Testsurname успешно обновлен", result);
    }

    @Test
    void updateUserNotFound() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        int userId = 1;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);
        UserService userService = new UserService(userRepository);

        Executable executable = () -> userService.updateUser(new User(), userId);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void deleteUserSuccess() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        int userId = 1;
        User user = new User(userId, "Testname", "Testsurname", "Male", "Russia",
                "79261134321", new Date(), "Testtext", "testuser@test.com", "123",
                "testurl", false, "Moscow");
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserService userService = new UserService(userRepository);

        String result = userService.deleteUser(userId);

        Assertions.assertEquals("Пользователь c id = 1 успешно удален", result);
        Assertions.assertTrue(user.isDeleted());
    }

    @Test
    void deleteUserNotFound() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        int userId = 1;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);
        UserService userService = new UserService(userRepository);

        Executable executable = () -> userService.deleteUser(userId);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void getUsersSuccess() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        List<User> userList = List.of(
                new User("Testname1", "Testsurname1", "Male", "Russia", "79261134321",
                        new Date(), "Testtext1", "testuser1@test.com", "123", "testurl1",
                        false, "Moscow"),
                new User("Testname2", "Testsurname2", "Male", "Russia", "79261134322",
                        new Date(), "Testtext2", "testuser2@test.com", "123", "testurl2",
                        false, "Moscow")
        );
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        UserService userService = new UserService(userRepository);

        List<User> result = userService.getUsers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Testname1", result.get(0).getName());
        Assertions.assertEquals("Testname2", result.get(1).getName());
    }

    @Test
    void getUsersEmptyList() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        List<User> emptyList = List.of();
        Mockito.when(userRepository.findAll()).thenReturn(emptyList);
        UserService userService = new UserService(userRepository);

        List<User> result = userService.getUsers();

        Assertions.assertTrue(result.isEmpty());
    }
}