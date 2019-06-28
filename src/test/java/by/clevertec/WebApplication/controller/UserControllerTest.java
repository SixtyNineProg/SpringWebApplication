package by.clevertec.WebApplication.controller;

import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

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

    ObjectMapper objectMapper = new ObjectMapper();

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists());
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
    public void whenPostRequestSaveInvalidUserThenExpectStatusNoContent() throws Exception {
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}