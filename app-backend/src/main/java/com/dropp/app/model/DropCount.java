package com.dropp.app.model;

import com.dropp.app.model.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropCount implements Serializable {

    private UserDetail user;
    private Long count;

}
