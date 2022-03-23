package com.dropp.app.model.dto;

import com.dropp.app.model.enums.Avatar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {

    private Long id;

    private String username;

    private String email;

    private String contactNo;

    private Long favDrops;

    private Avatar avatar;
}
