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

    /**
     * This method is responsible to find drop for the given user id and drop id.
     *
     * @param userId user id
     * @param id     drop id
     * @return drop details for the given drop id
     */

    public DropDetailDTO getDrop(Long userId, Long id) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        DropDetail dropDetail = dropDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Drop not found with id:" + id));
        return dropDetailTransformer.map(dropDetail);
    }

    /**
     * This method is responsible for getting all the drops of a particular user.
     *
     * @param userId user id
     * @return List of all the drops of the given user id
     */

    public List<DropDetailDTO> getDropsForUser(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        return dropDetailRepository.findByUser(userDetail)
                .stream()
                .map(dropDetail -> dropDetailTransformer.map(dropDetail))
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for adding a new drop for a user id.
     *
     * @param userId      user id
     * @param dropRequest drop request details
     * @return Drop Details for the newly added drop
     */

    public DropDetailDTO addDrop(Long userId, DropRequest dropRequest) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
        DropDetail dropDetail = dropDetailTransformer.map(dropRequest);
        dropDetail.setUser(userDetail);
        dropDetail.setActive(true);
        return dropDetailTransformer.map(dropDetailRepository.save(dropDetail));
    }

    /**
     * This method is responsible for starring a drop.
     *
     * @param userId user id
     * @param dropId drop id
     * @return Drop Details for the starred drop
     */

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

    /**
     * This method is responsible for un-starring a drop.
     *
     * @param userId user id
     * @param dropId drop id
     * @return Drop Details of the un-starred drop
     */

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

    /**
     * This method is responsible for getting all the starred drops of a particular user id.
     *
     * @param userId user id
     * @return List of all the starred drop details
     */

    public List<DropDetailDTO> getStarredDrops(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> throwUserNotFoundException(userId));
        List<StarredDrop> starredDrops = starredDropRepository.findByUserDetailAndIsActive(userDetail, true);
        return starredDrops.stream()
                .map(starredDrop -> starredDrop.getDropDetail())
                .map(dropDetail -> dropDetailTransformer.map(dropDetail))
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for marking a drop explored for a user id.
     *
     * @param userId user id
     * @param dropId drop id
     * @return Drop Details of the explored drop
     */

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

    /**
     * This method is responsible for creating a starred drop object.
     *
     * @param userDetail user details
     * @param dropDetail drop details
     * @return starred drop
     */

    private StarredDrop getStarredDrop(UserDetail userDetail, DropDetail dropDetail) {
        return StarredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail)
                .build();
    }

    /**
     * This method is responsible for getting all new drops which are under @param{radius} metres from @param{latitude}
     * and @param{longitude}.
     *
     * @param userId    user id
     * @param latitude  latitude in decimals
     * @param longitude longitude in decimals
     * @param radius    radius in metres
     * @return List of all the Drops
     */

    public List<Drop> getAllDropsForUser(Long userId, BigDecimal latitude, BigDecimal longitude, Long radius) {
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
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for getting the count of all the drops by a user id.
     *
     * @param startDate start date from the which the drops need to be counted
     * @param endDate   end date until which the drops need to be counted
     * @return List of all the users with their drop counts for the specified period
     */

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

    /**
     * This method is responsible for getting the count of all the drops explored by a user id.
     *
     * @param startDate start date from the which the drops need to be counted
     * @param endDate   end date until which the drops need to be counted
     * @return List of all the users with their explored drop counts for the specified period
     */

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

    /**
     * This method is responsible for creating a UserNotFoundException for a user id.
     *
     * @param userId user id
     * @return UserNotFoundException object
     */

    private UserNotFoundException throwUserNotFoundException(Long userId) {
        return new UserNotFoundException("User Not found with user id: " + userId);
    }

    /**
     * This method is responsible for creating a ResourceNotFoundException for a drop id.
     *
     * @param dropId drop id
     * @return ResourceNotFoundException object
     */

    private ResourceNotFoundException throwResourceNotFoundException(Long dropId) {
        return new ResourceNotFoundException("User Not found with user id: " + dropId);
    }

}
