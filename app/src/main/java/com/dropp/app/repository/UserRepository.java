package com.dropp.app.repository;

import com.dropp.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmailOrUsername(String email, String username);
}
