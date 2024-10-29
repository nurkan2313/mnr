package kg.core.mnr.models.entity;

import jakarta.persistence.*;
import kg.core.mnr.models.dto.enums.Role;
import kg.core.mnr.models.dto.requests.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    private UUID id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String patronymic;
    @Enumerated(EnumType.STRING)
    private Role role; // admin, customs, border, prosecutor

    public Users(RegisterRequest r) {
        this.id = UUID.randomUUID();
        this.email = r.getEmail();
        this.phone = r.getPhone().replaceAll("[()+-]", "");
        this.firstName = r.getFirstName();
        this.lastName = r.getLastName();
        this.patronymic = r.getPatronymic();
        this.role = r.getRole();
    }

}
