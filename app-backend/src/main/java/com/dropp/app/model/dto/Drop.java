package com.dropp.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drop implements Serializable {

    private DropDetailDTO dropDetail;

    private boolean isSeen;
}
