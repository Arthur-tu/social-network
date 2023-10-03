package com.socialNetwork.users.moduleTests.java.com.socialNetwork.users.controller;

import com.socialNetwork.users.DefaultModuleTest;
import com.socialNetwork.users.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerModuleTest extends DefaultModuleTest {
    @Test
    @DisplayName("Test: Создание пользователя")
    void createUserSuccessfully() throws Exception {
        User testUser = new User("test1","test1", "test1", "test1",
                "79999999913", new Date(), "teset!", "test-test@test.com", "113",
                "123", false, "Moscow");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Пользователь test1 добавлен с id = 1"));

    }
}
