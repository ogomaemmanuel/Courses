package ke.co.safaricom.weblog.user.service;

import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetailsService;

@Component
public class UserDetailsExtra implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsExtra(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
