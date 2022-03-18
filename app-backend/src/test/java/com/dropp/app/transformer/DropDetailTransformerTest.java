package com.dropp.app.transformer;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.model.enums.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DropDetailTransformerTest {

    @InjectMocks
    private DropDetailTransformer transformer;

    @Test
    public void testMap() {
        DropRequest dropRequest = DropRequest.builder()
                .type(EntityType.TEXT)
                .message("Hello!")
                .longitude(new BigDecimal(83.4))
                .latitude(new BigDecimal(2.34))
                .build();
        DropDetail dropDetail = transformer.map(dropRequest);
        assertNotNull(dropDetail);
        assertEquals("Hello!", dropDetail.getMessage());
        assertEquals(EntityType.TEXT, dropDetail.getType());
        assertEquals(new BigDecimal(2.34), dropDetail.getLatitude());
        assertEquals(new BigDecimal(83.4), dropDetail.getLongitude());
    }
}
