package kg.core.mnr.service;

import kg.core.mnr.exception.EmailOrPhoneAlreadyExists;
import kg.core.mnr.exception.InvalidPasswordException;
import kg.core.mnr.models.dto.enums.Role;
import kg.core.mnr.models.dto.requests.LoginRequest;
import kg.core.mnr.models.dto.requests.RegisterRequest;
import kg.core.mnr.models.dto.response.UserResponseDto;
import kg.core.mnr.models.entity.Users;
import kg.core.mnr.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponseDto register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null || userRepository.findByPhone(request.getPhone()) != null) {
            throw new EmailOrPhoneAlreadyExists();
        }
        Users user = new Users(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public Users auth(LoginRequest request) {
        Users user = userRepository.findByEmail(request.getEmailOrPhone());
        if (user == null) {
            user = userRepository.findByPhone(request.getEmailOrPhone());
            if (user == null) {
                throw new InvalidPasswordException();
            }
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    private Users getUserFromContext() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(emailOrPhone);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getGrantedAuthorities())
                .build();
    }
}
