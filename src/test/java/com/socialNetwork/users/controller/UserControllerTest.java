package com.socialNetwork.users.controller;

import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    private void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createUser() throws Exception {
        String request = "{\n" +
                "    \"name\": \"test1\",\n" +
                "    \"surname\": \"test1\",\n" +
                "    \"sex\": \"test1\",\n" +
                "    \"country\": \"test1\",\n" +
                "    \"phone\": \"79998999966\",\n" +
                "    \"birthDate\": \"2002-11-28\",\n" +
                "    \"profileText\": \"teset!\",\n" +
                "    \"email\": \"artur-tuinov@m.ru\",\n" +
                "    \"passwordHash\": \"113\",\n" +
                "    \"imgUrl\": \"123\",\n" +
                "    \"isDeleted\": false,\n" +
                "    \"city\": \"Moscow\"\n" +
                "}";

        Mockito.when(userService.createUser(any(User.class))).thenReturn("Пользователь test1 добавлен с id = 1");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.equalTo("Пользователь test1 добавлен с id = 1")));
    }
}