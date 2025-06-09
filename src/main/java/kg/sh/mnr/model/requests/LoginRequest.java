package kg.sh.mnr.model.requests;


import lombok.Getter;

@Getter
public class LoginRequest {
    String emailOrPhone;
    String password;

    public LoginRequest() {}

    public LoginRequest(String emailOrPhone, String password) {
        this.emailOrPhone = emailOrPhone;
        this.password = password;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
