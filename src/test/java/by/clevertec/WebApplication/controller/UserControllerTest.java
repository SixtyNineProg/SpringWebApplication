package by.clevertec.WebApplication.controller;

import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService<User> userService;

    private User user = new User(
            "1",
            "Andrey",
            "Andrey@mail.ru",
            "123456789",
            18);

    private User invalidUser = new User(
            null,
            null,
            "Andrey@mail.ru",
            "123456789",
            18);

    private List<User> users = Arrays.asList(
            new User("1",
                    "Andrey",
                    "Andrey@mail.ru",
                    "123456789",
                    14),
            new User("2",
                    "Victor",
                    "Victor@mail.ru",
                    "777756789",
                    26),
            new User("3",
                    "Kate",
                    "Kate@mail.ru",
                    "888856789",
                    10)
    );

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenGetRequestThenExpectStatusOkAndContextJSON() throws Exception {
        given(this.userService.getUser(anyString())).willReturn(java.util.Optional.of(user));
        this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetRequestThenExpectJSONContenNameAndPassword() throws Exception {
        given(this.userService.getUser(anyString())).willReturn(java.util.Optional.of(user));
        this.mockMvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.password").exists());
    }

    @Test
    public void whenGetRequestThenExpectStatusNotFound() throws Exception {
        given(this.userService.getUser(anyString())).willReturn(Optional.empty());
        this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPostRequestSaveUserThenExpectStatusOkAndIdAddedUser() throws Exception {
        given(this.userService.saveUser(user)).willReturn(user.getId());
        MvcResult mvcResult = this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        String id = mvcResult.getResponse().getContentAsString();
        assertEquals(id, user.getId());
    }

    @Test
    public void whenPostRequestSaveUserWithExceptonThenExpectStatusInternalServerError() throws Exception {
        given(this.userService.saveUser(user)).willThrow(new ArithmeticException());
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void whenPostRequestSaveInvalidUserThenExpectStatusNoContent() throws Exception {
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void whenPostRequestDeleteUserThenExpectStatusOkAndResponceTrue() throws Exception {
        given(userService.getUser(anyString())).willReturn(java.util.Optional.of(user));
        given(this.userService.deleteUser(anyString())).willReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/1"))
                .andExpect(status().isOk())
                .andReturn();

        Boolean responce = Boolean.valueOf(mvcResult.getResponse().getContentAsString());
        assertEquals(responce, true);
    }

    @Test
    public void whenPostRequestDeleteUserInvalidIdThenExpectStatusNotFound() throws Exception {
        given(this.userService.deleteUser(anyString())).willReturn(true);
        this.mockMvc.perform(post("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPostRequestDeleteUserWithExceptionThenExpectStatusInternalServerError() throws Exception {
        given(userService.getUser(anyString())).willReturn(java.util.Optional.of(user));
        given(this.userService.deleteUser(anyString())).willThrow(new ArithmeticException());
        this.mockMvc.perform(post("/user/1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void whenGetRequestGetAllThenExpectStatusOk() throws Exception {
        given(this.userService.getAllUsers()).willReturn(users);
        this.mockMvc.perform(get("/user/getAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetRequestGetAllThenExpectTrueData() throws Exception {
        given(this.userService.getAllUsers()).willReturn(users);
        this.mockMvc.perform(get("/user/getAll"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Andrey"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("Victor@mail.ru"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].age").value(10));
    }

    @Test
    public void whenGetRequestGetAllWhithExceptionThenExpectStatusInternalServerError() throws Exception {
        given(this.userService.getAllUsers()).willThrow(new ArithmeticException());
        this.mockMvc.perform(get("/user/getAll"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void whenPutRequestUpdateUserThenExpectStatusOk() throws Exception {
        given(this.userService.getUser(user.getId())).willReturn(Optional.ofNullable(user));
        given(this.userService.updateUser(user)).willReturn(true);
        this.mockMvc.perform(put("/user/updateUser")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPutRequestUpdateNullUserThenExpectStatusNotFound() throws Exception {
        given(this.userService.getUser(user.getId())).willReturn(Optional.empty());
        given(this.userService.updateUser(user)).willReturn(true);
        this.mockMvc.perform(put("/user/updateUser")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }


}