package com.dropp.app.transformer;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.model.dto.DropDetailDTO;
import org.springframework.stereotype.Service;

@Service
public class DropDetailTransformer {

    public DropDetail map(DropRequest dropRequest) {
        return DropDetail.builder()
                .type(dropRequest.getType())
                .latitude(dropRequest.getLatitude())
                .longitude(dropRequest.getLongitude())
                .message(dropRequest.getMessage())
                .url(dropRequest.getUrl())
                .build();
    }

    public DropDetailDTO map(DropDetail dropDetail) {
        return DropDetailDTO.builder()
                .id(dropDetail.getId())
                .userId(dropDetail.getUser().getId())
                .type(dropDetail.getType())
                .url(dropDetail.getUrl())
                .message(dropDetail.getMessage())
                .latitude(dropDetail.getLatitude())
                .longitude(dropDetail.getLongitude())
                .createdAt(dropDetail.getCreatedAt())
                .updatedAt(dropDetail.getUpdatedAt())
                .build();
    }
}
