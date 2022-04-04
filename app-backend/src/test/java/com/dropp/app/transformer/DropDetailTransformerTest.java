package com.dropp.app.transformer;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.model.UserDetail;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.model.enums.Avatar;
import com.dropp.app.model.enums.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DropDetailTransformerTest {

    @Mock
    private GeometryFactory geometryFactory;

    @InjectMocks
    private DropDetailTransformer transformer;

    @Test
    public void testMap1() {
        DropRequest dropRequest = DropRequest.builder()
                .type(EntityType.TEXT)
                .message("Hello!")
                .longitude(new BigDecimal(83.4))
                .latitude(new BigDecimal(2.34))
                .build();
        GeometryFactory factory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(dropRequest.getLongitude().doubleValue(), dropRequest.getLatitude().doubleValue());
        Point point = factory.createPoint(coordinate);
        when(this.geometryFactory.createPoint(eq(coordinate))).thenReturn(point);
        DropDetail dropDetail = transformer.map(dropRequest);
        assertNotNull(dropDetail);
        assertEquals("Hello!", dropDetail.getMessage());
        assertEquals(EntityType.TEXT, dropDetail.getType());
        assertEquals(point, dropDetail.getCoordinate());
    }

    @Test
    public void testMap2() {
        Instant time = Instant.now();
        GeometryFactory geometryFactory = new GeometryFactory();
        UserDetail userDetail = UserDetail.builder()
                .id(1l)
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .avatar(Avatar.AV1)
                .build();
        DropDetail dropDetail = DropDetail.builder()
                .id(1l)
                .user(userDetail)
                .type(EntityType.TEXT)
                .message("Hello")
                .coordinate(geometryFactory.createPoint(new Coordinate(12.43, 8.34)))
                .isActive(true)
                .build();
        DropDetailDTO actualDropDetailDTO = transformer.map(dropDetail);
        assertEquals(dropDetail.getId(), actualDropDetailDTO.getId());
        assertEquals(dropDetail.getType(), actualDropDetailDTO.getType());
        assertEquals(dropDetail.getMessage(), actualDropDetailDTO.getMessage());
        assertEquals(BigDecimal.valueOf(dropDetail.getCoordinate().getY()), actualDropDetailDTO.getLatitude());
        assertEquals(BigDecimal.valueOf(dropDetail.getCoordinate().getX()), actualDropDetailDTO.getLongitude());
    }
}