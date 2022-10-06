package ke.co.safaricom.weblog.user.controller;

import ke.co.safaricom.weblog.user.dto.UserCreationRequest;
import ke.co.safaricom.weblog.user.dto.UserRoleAssignment;
import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        var user = userService.getUserById(id);
        return ResponseEntity.of(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserCreationRequest newUser){
        var user = userService.createUser(newUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/add-role")
    public ResponseEntity<User> addRoleToUser(@RequestBody UserRoleAssignment newUser){
        var user = userService.addRoleToUser(newUser);
        return ResponseEntity.of(user);
    }

}
