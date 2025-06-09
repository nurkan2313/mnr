package kg.sh.mnr.service;

import kg.sh.mnr.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
