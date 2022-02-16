package com.dropp.app.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_detail", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
@Validated
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @NotEmpty
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "username")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 30)
    @Column(name = "password")
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 35)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 35)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "fav_drops")
    private int favDrops;

}
