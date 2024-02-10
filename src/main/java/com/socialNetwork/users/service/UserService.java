package com.socialNetwork.users.service;

import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        if (userRepository.findByPhoneAndEmail(user.getPhone(), user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        User savedUser = userRepository.save(user);
        return String.format("Пользователь %s добавлен с id = %s", savedUser.getName(), savedUser.getId());
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateUser(User user, int id, boolean isAdmin, String preferred_username, String email) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!isAdmin && !user.getName().equals(preferred_username) && !user.getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        User savedUser = userRepository.save(user);
        return String.format("Пользователь %s успешно обновлен", savedUser.getSurname());
    }

    public String deleteUser(int id, boolean isAdmin, String preferred_username, String email) {
        User deletedUser = userRepository.findById(id).orElseThrow(() -> new
                ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!isAdmin && !deletedUser.getName().equals(preferred_username) && !deletedUser.getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        deletedUser.setDeleted(true);
        userRepository.save(deletedUser);
        return String.format("Пользователь c id = %s успешно удален", id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
