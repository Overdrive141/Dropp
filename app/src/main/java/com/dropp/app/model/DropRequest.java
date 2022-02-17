package com.dropp.app.model;

import com.dropp.app.model.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class DropRequest {

    @NotNull
    private EntityType type;

    @Size(max = 1000)
    private String message;

    @Size(max = 1000)
    private String url;

    @Digits(integer = 8, fraction = 6)
    private BigDecimal latitude;

    @Digits(integer = 9, fraction = 6)
    private BigDecimal longitude;
}
