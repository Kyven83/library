package com.amirsaleh.library.infrastructure;

import com.amirsaleh.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByNationalCode(String nationalCode);

    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByNationalCode(String nationalCode);

    User getUserById(UUID id);
}
