package com.movii.test.movii_test.repository;

import com.movii.test.movii_test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdentity(String identity);

    Optional<User> findByEmail(String email);

    boolean existsByIdentity(String identity);

    boolean existsByEmail(String email);
}
