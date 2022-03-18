package com.dropp.app.service;

import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.repository.DropDetailRepository;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.DropDetailTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DropDetailService {

    private final DropDetailRepository dropDetailRepository;
    private final DropDetailTransformer transformer;
    private final UserDetailRepository userDetailRepository;

    public DropDetail getDrop(Long id) {
        return dropDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Drop not found with id:" + id));
    }

    public List<DropDetail> getDropsForUser(Long userId) {
        existsById(userId);
        return dropDetailRepository.findByUserId(userId);
    }

    public DropDetail addDrop(Long userId, DropRequest dropRequest) {
        existsById(userId);
        DropDetail dropDetail = transformer.map(dropRequest);
        dropDetail.setUserId(userId);
        dropDetail.setActive(true);
        return dropDetailRepository.save(dropDetail);
    }

    private void existsById(Long userId) {
        if (!userDetailRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id:" + userId);
        }
    }
}
