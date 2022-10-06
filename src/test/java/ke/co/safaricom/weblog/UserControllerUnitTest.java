package ke.co.safaricom.weblog;

import ke.co.safaricom.weblog.user.controller.UserController;
import ke.co.safaricom.weblog.user.dto.UserCreationRequest;
import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.service.UserDetailsExtra;
import ke.co.safaricom.weblog.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
@AutoConfigureMockMvc
public class UserControllerUnitTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserDetailsExtra userDetailsExtra;
    @MockBean
    UserService userService;

    @Test
    @WithMockUser
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUser()).thenReturn(List.of(new User()));
        mockMvc.perform((get("/user"))).andExpect(status().isOk());
    }

    @Test
    public void test_user_not_get_all_user_when_not_authenticated() throws Exception {
        when(userService.getAllUser()).thenReturn(List.of(new User()));
        mockMvc.perform((get("/user"))).andExpect(status().isUnauthorized());
    }

    @Test
    public void test_can_not_create_user_when_not_authenticated() throws Exception {
        when(userService.createUser(any(UserCreationRequest.class))).thenReturn(new User());
        mockMvc.perform(post("/user").content("""
                {
                "username" : "test",
                "password":"test"
                }
                """).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    @DisplayName("Test Create User When Authenticated")
    public void test_can_create_user_when_authenticated() throws Exception {
        when(userService.createUser(any(UserCreationRequest.class))).thenReturn(new User());
        mockMvc.perform(post("/user").content("""
                {
                "username" : "test",
                "password":"test"
                }
                """).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    @DisplayName("Test Create User Fails when Username not Supplied")
    public void testCreateUserWithBlankUsername() throws Exception {
        when(userService.createUser(any(UserCreationRequest.class))).thenReturn(new User());
        mockMvc.perform(post("/user").content("""
                {
                "username" : "",
                "password":"test"
                }
                """).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }
}
