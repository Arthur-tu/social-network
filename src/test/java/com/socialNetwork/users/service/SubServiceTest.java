package com.socialNetwork.users.service;

import com.socialNetwork.users.entity.Sub;
import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.repository.SubRepository;
import com.socialNetwork.users.repository.UserRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SubServiceTest {

    private SubRepository subRepository;
    private UserRepository userRepository;
    private SubService subService;

    @BeforeEach
    private void init() {
        subRepository = Mockito.mock(SubRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        subService = new SubService(subRepository, userRepository);
    }


    @Test
    void followSuccess() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sub sub = new Sub(1, 2, date);
        Sub savedSub = new Sub(1,1,2,date);
        User currentUser = new User(1,"Testname", "Testsurname", "Male", "Russia","79261134321",
                date,"Testtext", "testuser@test.com", "123", "testurl",
                false, "Moscow");
        User targetUser = new User(2, "Testname", "Testsurname", "Male", "Russia",
                "79261134321", date,"Testtext", "testuser@test.com", "123",
                "testurl", false, "Moscow");
        when(userRepository.findById(1)).thenReturn(Optional.of(currentUser));
        when(userRepository.findById(2)).thenReturn(Optional.of(targetUser));
        when(subRepository.save(sub)).thenReturn(savedSub);
        SubService subService = new SubService(subRepository, userRepository);

        String result = subService.follow(sub);

        Assertions.assertEquals("Пользователь с id = 1 подписался на пользователя с id = 2", result);
    }

    @Test
    void followError() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sub sub = new Sub(1, 2, date);
        Mockito.when(subRepository.save(sub)).thenThrow(ResponseStatusException.class);

        Executable executable = () -> subService.follow(sub);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void getSubSuccess() {
        int subId = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sub sub = new Sub(subId, 1, 2, date);
        Mockito.when(subRepository.findById(subId)).thenReturn(Optional.of(sub));

        Sub result = subService.getSub(subId);

        Assertions.assertEquals(sub, result);
    }

    @Test
    void getSubError() {
        int subId = 1;
        Mockito.when(subRepository.findById(subId)).thenReturn(Optional.empty());

        Executable executable = () -> subService.getSub(subId);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }
    @Test
    void updateSubSuccess() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sub sub = new Sub(1, 1, 2, date);
        Mockito.when(subRepository.existsById(1)).thenReturn(true);
        Mockito.when(subRepository.save(sub)).thenReturn(sub);

        String result = subService.updateSub(sub, 1);

        Assertions.assertEquals("Подписка с id = 1 успешно обновлена", result);
    }

    @Test
    void updateSubNotFound() {
        int subId = 1;
        Mockito.when(subRepository.existsById(subId)).thenReturn(false);

        Executable executable = () -> subService.updateSub(new Sub(), subId);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void unfollowSuccess() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2012-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sub sub = new Sub(1, 2, date);

        Mockito.when(subRepository.existsById(1)).thenReturn(true);
        Mockito.when(subRepository.findById(1)).thenReturn(Optional.of(sub));

        String result = subService.unfollow(1);

        Assertions.assertEquals("Подписка с id = 1 удалена", result);
    }

    @Test
    void unfollowNotFound() {
        int subId = 1;
        Mockito.when(subRepository.existsById(subId)).thenReturn(false);

        Executable executable = () -> subService.unfollow(subId);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void getAllSubSuccess() {
        List<Sub> subList = List.of(
                new Sub(2,3, new Date()),
                new Sub(3, 2, new Date()));

        Mockito.when(subRepository.findAll()).thenReturn(subList);

        List<Sub> result = subService.getAllSubs();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(2, result.get(0).getUserId());
        Assertions.assertEquals(3, result.get(1).getUserId());
    }

    @Test
    void getAllSubEmptyList() {
        List<Sub> emptyList = List.of();
        Mockito.when(subRepository.findAll()).thenReturn(emptyList);

        List<Sub> result = subService.getAllSubs();

        Assertions.assertTrue(result.isEmpty());
    }
}