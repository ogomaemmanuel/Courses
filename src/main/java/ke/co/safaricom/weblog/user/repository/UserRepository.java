package ke.co.safaricom.weblog.user.repository;

import ke.co.safaricom.weblog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
