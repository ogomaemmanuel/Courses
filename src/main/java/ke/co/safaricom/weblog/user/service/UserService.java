package ke.co.safaricom.weblog.user.service;

import ke.co.safaricom.weblog.profile.entity.Profile;
import ke.co.safaricom.weblog.profile.repository.ProfileRepository;
import ke.co.safaricom.weblog.role.repository.RoleRepository;
import ke.co.safaricom.weblog.user.dto.UserCreationRequest;
import ke.co.safaricom.weblog.user.dto.UserRoleAssignment;
import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User createUser(UserCreationRequest newUser){
        var user = new User();
        var profile = new Profile();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        profile.setUsername(newUser.getUsername());
        userRepository.save(user);
        profileRepository.save(profile);
        return user;
    }

    public Optional<User> addRoleToUser(UserRoleAssignment newRole){
        var user = userRepository.findById(newRole.getUserId());
        var role = roleRepository.findByName(newRole.getRole());
        user.ifPresent(u->{
            u.addRoleToUser(role);
            userRepository.save(u);
        });
        return user;
    }

}
