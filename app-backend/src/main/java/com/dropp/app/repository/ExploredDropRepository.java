package com.dropp.app.repository;

import com.dropp.app.model.DropCount;
import com.dropp.app.model.DropDetail;
import com.dropp.app.model.ExploredDrop;
import com.dropp.app.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ExploredDropRepository extends JpaRepository<ExploredDrop, Long> {

    Optional<ExploredDrop> findByUserDetailAndDropDetail(UserDetail userDetail, DropDetail dropDetail);

    @Query(value = "Select new com.dropp.app.model.DropCount(e.userDetail, count(e.id)) from ExploredDrop e " +
            "where e.createdAt between :startDate and :endDate " +
            "group by e.userDetail order by count(e.id) desc")
    List<DropCount> findExploreCountByUser(Instant startDate, Instant endDate);
}
