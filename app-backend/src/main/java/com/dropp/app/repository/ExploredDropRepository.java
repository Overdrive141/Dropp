package com.dropp.app.repository;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.ExploredDrop;
import com.dropp.app.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExploredDropRepository extends JpaRepository<ExploredDrop, Long> {

    Optional<ExploredDrop> findByUserDetailAndDropDetail(UserDetail userDetail, DropDetail dropDetail);
}
