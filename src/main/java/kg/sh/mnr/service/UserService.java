package kg.sh.mnr.service;

import kg.sh.mnr.entity.Users;
import kg.sh.mnr.model.requests.RegisterRequest;
import kg.sh.mnr.model.response.UserResponseDto;
import kg.sh.mnr.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto register(RegisterRequest request) {
        Users user = new Users(request);
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}