package com.dropp.app.transformer;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.model.dto.DropDetailDTO;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DropDetailTransformer {

    private final GeometryFactory geometryFactory;

    /**
     * This method transforms DropRequest object into DropDetail object.
     *
     * @param dropRequest Drop Request
     * @return Drop Detail
     */

    public DropDetail map(DropRequest dropRequest) {
        return DropDetail.builder()
                .type(dropRequest.getType())
                .message(dropRequest.getMessage())
                .url(dropRequest.getUrl())
                .coordinate(geometryFactory.createPoint(new Coordinate(dropRequest.getLongitude().doubleValue(), dropRequest.getLatitude().doubleValue())))
                .build();
    }

    /**
     * This method transforms DropDetail object into DropDetailDTO object.
     *
     * @param dropDetail DropDetail object
     * @return DropDetailDTO object
     */

    public DropDetailDTO map(DropDetail dropDetail) {
        return DropDetailDTO.builder()
                .id(dropDetail.getId())
                .userId(dropDetail.getUser().getId())
                .type(dropDetail.getType())
                .url(dropDetail.getUrl())
                .message(dropDetail.getMessage())
                .latitude(BigDecimal.valueOf(dropDetail.getCoordinate().getY()))
                .longitude(BigDecimal.valueOf(dropDetail.getCoordinate().getX()))
                .createdAt(dropDetail.getCreatedAt())
                .updatedAt(dropDetail.getUpdatedAt())
                .build();
    }
}
