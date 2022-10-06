package ke.co.safaricom.weblog.user.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class UserCreationRequest {

    @NotBlank(message = "Username is required")
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
