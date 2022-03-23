package com.dropp.app.model.dto;

import com.dropp.app.model.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropDetailDTO implements Serializable {

    private Long id;

    private Long userId;

    private EntityType type;

    private String message;

    private String url;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Instant createdAt;

    private Instant updatedAt;

}
