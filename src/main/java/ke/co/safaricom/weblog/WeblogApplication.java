package ke.co.safaricom.weblog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@OpenAPIDefinition(info = @Info(title ="Blog Application",version = "5",contact =@Contact(name ="Emmanuel Ogoma",email = "eogoma@gmail.com")))
@SecurityScheme(name = "basic-auth",scheme = "basic", type= SecuritySchemeType.HTTP,in= SecuritySchemeIn.HEADER)
public class WeblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogApplication.class, args);
    }

}
