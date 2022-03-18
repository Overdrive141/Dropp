package com.dropp.app.model;

import com.dropp.app.model.enums.Avatar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_detail")
@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail extends DateAudit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "username", unique = true)
    private String username;

    @JsonIgnore
    @Size(min = 8, max = 30)
    @Column(name = "password")
    private String password;

    @NotNull
    @NotEmpty
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "fav_drops")
    private Long favDrops;

    @NotEmpty
    @NotNull
    @Size(max = 5)
    @Enumerated(EnumType.ORDINAL)
    private Avatar avatar;

}
