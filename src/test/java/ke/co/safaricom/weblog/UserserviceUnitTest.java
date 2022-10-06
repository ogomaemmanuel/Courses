package ke.co.safaricom.weblog;

import ke.co.safaricom.weblog.profile.repository.ProfileRepository;
import ke.co.safaricom.weblog.role.repository.RoleRepository;
import ke.co.safaricom.weblog.user.dto.UserCreationRequest;
import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.repository.UserRepository;
import ke.co.safaricom.weblog.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserserviceUnitTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ProfileRepository profileRepositoryMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void testGetAllUser()
    {when(userRepositoryMock.findAll()).thenReturn(new ArrayList<>());
     var result= userService.getAllUser();
     verify(userRepositoryMock,times(1)).findAll();
    }

    @Test
    public void testGetUserById(){
        var user = new User();
        user.setUsername("Test");
        when(userRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(user)).thenReturn(Optional.of(new  User()));
        var result=   userService.getUserById(1L);
        verify(userRepositoryMock).findById(anyLong());
        Assertions.assertEquals("Test",result.get().getUsername());
    }



    @Test
    public void testAddUser() {
        UserCreationRequest userCreationRequest= new UserCreationRequest();
        userCreationRequest.setUsername("test");
        userCreationRequest.setPassword("test1");
        when(userRepositoryMock.save(any(User.class))).thenReturn(any(User.class));
        userService.createUser(userCreationRequest);
        verify(userRepositoryMock).save(userArgumentCaptor.capture());
        var savedUser= userArgumentCaptor.getValue();
        Assertions.assertEquals(savedUser.getUsername(), userCreationRequest.getUsername());
    }

}
