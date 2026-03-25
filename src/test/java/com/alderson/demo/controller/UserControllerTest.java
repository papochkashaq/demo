package com.alderson.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.alderson.demo.model.User;
import com.alderson.demo.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static UUID id;
    private static User user1;
    private static List<User> users;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;

    @BeforeAll
    static void beforeAll() {
        id = UUID.randomUUID();
        user1 = new User(id, "A", "testA@mail.com", LocalDate.of(2000, 1, 1), Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()));
        User user2 = new User(UUID.randomUUID(), "B", "testB@mail.com", LocalDate.of(2000, 2, 2),
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        User user3 = new User(UUID.randomUUID(), "C", "testC@mail.com", LocalDate.of(2000, 3, 3),
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        users = List.of(user1, user2, user3);
    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(3)).andExpect(jsonPath("$[0].name").value("A")).andExpect(jsonPath("$[0].email").value("testA@mail.com"));
    }

    @Test
    void addUser() throws Exception {
        when(userService.addUser(any())).thenReturn(user1);
        String userJson = objectMapper.writeValueAsString(user1);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name").value("A")).andExpect(jsonPath("$.email").value("testA@mail.com"));
    }

    @Test
    void updateUser() throws Exception {
        User user = new User(UUID.randomUUID(), "A", "testA@mail.com", LocalDate.of(2000, 1, 1),
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(userService.updateUser(any())).thenReturn(user);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name").value("A")).andExpect(jsonPath("$.email").value("testA@mail.com"));
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(id);
        mockMvc.perform(delete("/users/" + id)).andExpect(status().isNoContent());
    }
}