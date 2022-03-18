package com.dropp.app.model;

import com.dropp.app.model.enums.Avatar;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@Validated
public class UserRequest {
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 20)
    private String username;

    @Size(min = 8, max = 30)
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @Size(min = 10, max = 10)
    private String contactNo;

    @NotEmpty
    @NotNull
    @Size(max = 5)
    @Enumerated(EnumType.ORDINAL)
    private Avatar avatar;
}
