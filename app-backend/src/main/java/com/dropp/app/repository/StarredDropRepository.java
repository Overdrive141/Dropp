package com.dropp.app.repository;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.StarredDrop;
import com.dropp.app.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarredDropRepository extends JpaRepository<StarredDrop, Long> {

    List<StarredDrop> findByUserDetailAndIsActive(UserDetail userDetail, Boolean isActive);

    Optional<StarredDrop> findByUserDetailAndDropDetail(UserDetail userDetail, DropDetail dropDetail);
}
