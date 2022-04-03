package com.dropp.app.repository;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.UserDetail;
import com.dropp.app.model.DropCount;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DropDetailRepository extends JpaRepository<DropDetail, Long> {

    Optional<DropDetail> findById(Long id);

    List<DropDetail> findByUser(UserDetail user);

    @Query(value = "Select d from DropDetail d where ST_Distance_Sphere(d.coordinate, :point) < :radius")
    List<DropDetail> findAllDrops(Point point, Long radius);
    
    @Query(value = "Select new com.dropp.app.model.DropCount(d.user, count(d.id)) from DropDetail d " +
            "where d.isActive = true and (d.createdAt between :startDate and :endDate) " +
            "group by d.user order by count(d.id) desc")
    List<DropCount> findDropCountByUser(Instant startDate, Instant endDate);
}
