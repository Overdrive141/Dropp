package com.dropp.app.model;

import com.dropp.app.model.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "drop")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropDetail extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType type;

    @Size(max = 1000)
    @Column(name = "message")
    private String message;

    @Size(max = 1000)
    @Column(name = "url")
    private String url;

    @Column(name = "latitude", precision = 8, scale = 6, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6, nullable = false)
    private BigDecimal longitude;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
