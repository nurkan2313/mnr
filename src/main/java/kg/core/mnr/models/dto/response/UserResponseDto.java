package kg.core.mnr.models.dto.response;

import kg.core.mnr.models.entity.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserResponseDto {
    String email;
    String phone;
    String name;
    String surname;
    String patronymic;

    public UserResponseDto(Users user) {
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.name = user.getFirstName() == null ? null : user.getFirstName();
        this.surname = user.getLastName() == null ? null : user.getLastName();
        this.patronymic = user.getPatronymic() == null ? null : user.getPatronymic();
    }

}