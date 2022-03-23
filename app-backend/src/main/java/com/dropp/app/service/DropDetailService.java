package com.dropp.app.service;

import com.dropp.app.exception.DropNotStarredException;
import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.*;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.repository.DropDetailRepository;
import com.dropp.app.repository.StarredDropRepository;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.DropDetailTransformer;
import com.dropp.app.transformer.ExploredDropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final DropDetailTransformer transformer;

    public DropDetailDTO getDrop(Long id) {
        DropDetail dropDetail = dropDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Drop not found with id:" + id));
        return transformer.map(dropDetail);
    }

    public List<DropDetailDTO> getDropsForUser(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
        return dropDetailRepository.findByUser(userDetail)
                .stream()
                .map(dropDetail -> transformer.map(dropDetail))
                .collect(Collectors.toList());
    }

    public DropDetailDTO addDrop(Long userId, DropRequest dropRequest) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
        DropDetail dropDetail = transformer.map(dropRequest);
        dropDetail.setUser(userDetail);
        dropDetail.setActive(true);
        return transformer.map(dropDetailRepository.save(dropDetail));
    }

    public DropDetailDTO starDrop(Long userId, Long dropId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not found with user id: " + userId));
        DropDetail dropDetail = dropDetailRepository.findById(dropId).orElseThrow(() -> new ResourceNotFoundException("Drop not found with drop id:" + dropId));
        StarredDrop starredDrop = starredDropRepository.findByUserDetailAndDropDetail(userDetail, dropDetail).orElse(getStarredDrop(userDetail, dropDetail));
        starredDrop.setActive(true) ;
        starredDropRepository.save(starredDrop);
        return transformer.map(dropDetail);
    }

    public DropDetailDTO unstarDrop(Long userId, Long dropId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not found with user id: " + userId));
        DropDetail dropDetail = dropDetailRepository.findById(dropId).orElseThrow(() -> new ResourceNotFoundException("Drop not found with drop id:" + dropId));
        StarredDrop starredDrop = starredDropRepository.findByUserDetailAndDropDetail(userDetail, dropDetail).orElseThrow(() -> new DropNotStarredException("Drop id: " + dropId + " is not starred by user id:" + userId));
        starredDrop.setActive(false);
        starredDropRepository.save(starredDrop);
        return transformer.map(dropDetail);
    }

    public Set<DropDetailDTO> getStarredDrops(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not found with user id: " + userId));
        List<StarredDrop> starredDrops = starredDropRepository.findByUserDetailAndIsActive(userDetail, true);
        return starredDrops.stream()
                .map(starredDrop -> dropDetailRepository.findById(starredDrop.getDropDetail().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(dropDetail -> transformer.map(dropDetail))
                .collect(Collectors.toSet());
    }

    public DropDetailDTO exploreDrop(Long userId, Long dropId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not found with user id: " + userId));
        DropDetail dropDetail = dropDetailRepository.findById(dropId).orElseThrow(() -> new ResourceNotFoundException("Drop not found with drop id:" + dropId));
        ExploredDrop exploredDrop = ExploredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail)
                .isSeen(true)
                .build();
        exploredDropRepository.save(exploredDrop);
        return transformer.map(dropDetail);
    }

    private StarredDrop getStarredDrop(UserDetail userDetail, DropDetail dropDetail) {
        return StarredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail)
                .build();
    }

}
