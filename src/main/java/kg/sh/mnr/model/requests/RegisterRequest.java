package kg.sh.mnr.model.requests;

import kg.sh.mnr.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    String email;
    String password;
    String phone;
    String firstName;
    String lastName;
    String patronymic;
    Role role;

    public RegisterRequest() {}

    public RegisterRequest(String email, String password, String phone, String name, String surname, String patronymic, Role role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.firstName = name;
        this.lastName = surname;
        this.patronymic = patronymic;
        this.role = role;
    }
}
