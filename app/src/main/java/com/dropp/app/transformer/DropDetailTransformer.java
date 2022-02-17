package com.dropp.app.transformer;

import com.dropp.app.model.DropRequest;
import com.dropp.app.model.DropDetail;
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
}
