package ke.co.safaricom.weblog;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import ke.co.safaricom.weblog.user.entity.User;
import ke.co.safaricom.weblog.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserRepositoryTest {
    private MySQLContainer<?> container =(MySQLContainer<?>) (new MySQLContainer("mysql:8")
            .withPassword("sa").withDatabaseName("blogs").withExposedPorts(3306).withCreateContainerCmdModifier((cmd)->{
                new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(3307), new ExposedPort(3306)));
            }));



    @BeforeEach
    public void setup() throws Exception {
        container.start();
    }
    @AfterEach
    public void teardown() throws Exception {
        container.stop();
    }

    @Autowired
    UserRepository userRepository ;
    @ParameterizedTest
    @CsvSource(textBlock = """
            test,test,
            test2,test2
            """)
    public void testSaveUser(String userName, String userPassword){
        User user = new User();
        user.setUsername(userName);
        user.setPassword(userPassword);
       userRepository.save(user);
       Assertions.assertEquals(1,user.getId());
    }


}
