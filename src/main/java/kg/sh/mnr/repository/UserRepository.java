package kg.sh.mnr.repository;

import kg.sh.mnr.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByEmail(String email);

    Users findByPhone(String phone);
}
