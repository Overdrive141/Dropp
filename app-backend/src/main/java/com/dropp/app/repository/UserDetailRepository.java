package com.dropp.app.repository;

import com.dropp.app.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    Optional<UserDetail> findByEmailOrUsername(String email, String username);


}
