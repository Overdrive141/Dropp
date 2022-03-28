package com.dropp.app.service;

import com.dropp.app.exception.DropNotStarredException;
import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.*;
import com.dropp.app.model.dto.Drop;
import com.dropp.app.model.dto.DropCountDTO;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.repository.DropDetailRepository;
import com.dropp.app.repository.ExploredDropRepository;
import com.dropp.app.repository.StarredDropRepository;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.DropDetailTransformer;
import com.dropp.app.transformer.UserDetailTransformer;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DropDetailService {

    private final UserDetailRepository userDetailRepository;
    private final DropDetailRepository dropDetailRepository;
    private final StarredDropRepository starredDropRepository;
    private final ExploredDropRepository exploredDropRepository;
    private final DropDetailTransformer dropDetailTransformer;
    private final UserDetailTransformer userDetailTransformer;
    private final GeometryFactory geometryFactory;

    public DropDetailDTO getDrop(Long userId, Long id) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        DropDetail dropDetail = dropDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Drop not found with id:" + id));
        return dropDetailTransformer.map(dropDetail);
    }

    public List<DropDetailDTO> getDropsForUser(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        return dropDetailRepository.findByUser(userDetail)
                .stream()
                .map(dropDetail -> dropDetailTransformer.map(dropDetail))
                .collect(Collectors.toList());
    }

    public DropDetailDTO addDrop(Long userId, DropRequest dropRequest) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
        DropDetail dropDetail = dropDetailTransformer.map(dropRequest);
        dropDetail.setUser(userDetail);
        dropDetail.setActive(true);
        return dropDetailTransformer.map(dropDetailRepository.save(dropDetail));
    }

    public DropDetailDTO starDrop(Long userId, Long dropId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        DropDetail dropDetail = dropDetailRepository.findById(dropId).orElseThrow(() -> throwResourceNotFoundException(dropId));
        StarredDrop starredDrop = starredDropRepository.findByUserDetailAndDropDetail(userDetail, dropDetail).orElse(getStarredDrop(userDetail, dropDetail));
        starredDrop.setActive(true);
        starredDropRepository.save(starredDrop);
        userDetail.setFavDrops(userDetail.getFavDrops() + 1);
        userDetailRepository.save(userDetail);
        return dropDetailTransformer.map(dropDetail);
    }

    public DropDetailDTO unstarDrop(Long userId, Long dropId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        DropDetail dropDetail = dropDetailRepository.findById(dropId).orElseThrow(() -> throwResourceNotFoundException(dropId));
        StarredDrop starredDrop = starredDropRepository.findByUserDetailAndDropDetail(userDetail, dropDetail).orElseThrow(() -> new DropNotStarredException("Drop id: " + dropId + " is not starred by user id:" + userId));
        starredDrop.setActive(false);
        starredDropRepository.save(starredDrop);
        userDetail.setFavDrops(userDetail.getFavDrops() - 1);
        userDetailRepository.save(userDetail);
        return dropDetailTransformer.map(dropDetail);
    }

    public Set<DropDetailDTO> getStarredDrops(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        List<StarredDrop> starredDrops = starredDropRepository.findByUserDetailAndIsActive(userDetail, true);
        return starredDrops.stream()
                .map(starredDrop -> starredDrop.getDropDetail())
                .map(dropDetail -> dropDetailTransformer.map(dropDetail))
                .collect(Collectors.toSet());
    }

    public DropDetailDTO exploreDrop(Long userId, Long dropId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        DropDetail dropDetail = dropDetailRepository.findById(dropId).orElseThrow(() -> throwResourceNotFoundException(dropId));
        ExploredDrop exploredDrop = ExploredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail)
                .isSeen(true)
                .build();
        exploredDropRepository.save(exploredDrop);
        return dropDetailTransformer.map(dropDetail);
    }

    private StarredDrop getStarredDrop(UserDetail userDetail, DropDetail dropDetail) {
        return StarredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail)
                .build();
    }

    public Set<Drop> getAllDropsForUser(Long userId, BigDecimal latitude, BigDecimal longitude, Long radius) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        Point point = geometryFactory.createPoint(new Coordinate(longitude.doubleValue(), latitude.doubleValue()));
        List<DropDetail> dropDetails = dropDetailRepository.findAllDrops(point, radius);
        return dropDetails.stream()
                .map(d -> {
                    Optional<ExploredDrop> exploredDrop = exploredDropRepository.findByUserDetailAndDropDetail(d.getUser(), d);
                    return Drop.builder()
                            .dropDetail(dropDetailTransformer.map(d))
                            .isSeen(exploredDrop.isPresent() && exploredDrop.get().isSeen())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    public List<DropCountDTO> getDropCountByUser(Instant startDate, Instant endDate) {
        List<DropCount> dropCounts = dropDetailRepository.findDropCountByUser(startDate, endDate);
        return dropCounts.stream()
                .map(dropCount -> {
                    return DropCountDTO.builder()
                            .user(userDetailTransformer.map(dropCount.getUser()))
                            .count(dropCount.getCount())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<DropCountDTO> getExploreCountByUser(Instant startDate, Instant endDate) {
        List<DropCount> exploreCounts = exploredDropRepository.findExploreCountByUser(startDate, endDate);
        return exploreCounts.stream()
                .map(exploreCount -> {
                    return DropCountDTO.builder()
                            .user(userDetailTransformer.map(exploreCount.getUser()))
                            .count(exploreCount.getCount())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private UserNotFoundException throwUserNotFoundException(Long userId) {
        return new UserNotFoundException("User Not found with user id: " + userId);
    }

    private ResourceNotFoundException throwResourceNotFoundException(Long dropId) {
        return new ResourceNotFoundException("User Not found with user id: " + dropId);
    }

}
