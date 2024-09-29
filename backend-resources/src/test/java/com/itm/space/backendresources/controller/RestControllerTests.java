package com.itm.space.backendresources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userService.deleteAllUsers();
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    @DisplayName("Test create user by MODERATOR functionality")
    public void givenUserDtoToSaveByModerator_whenCreateUser_thenSuccessResponse() throws Exception {
        //given
        UserRequest userDto = new UserRequest(
                "john",
                "john.doe@gmail.com",
                "rootroot",
                "John",
                "Doe");
        //when
        ResultActions result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    @DisplayName("Test create user duplicate by MODERATOR functionality")
    public void givenUserDtoToSaveWithDuplicate_whenCreateUser_thenConflictResponse() throws Exception {
        //given
        UserRequest userDto = new UserRequest(
                "john",
                "john.doe@gmail.com",
                "rootroot",
                "John",
                "Doe");

        userService.createUser(userDto);
        //when
        ResultActions result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Test create user by user functionality")
    public void givenUserDtoToSaveByUser_whenCreateUser_thenSuccessResponse() throws Exception {
        //given
        UserRequest userDto = new UserRequest(
                "john",
                "john.doe@gmail.com",
                "rootroot",
                "John",
                "Doe");
        //when
        ResultActions result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
