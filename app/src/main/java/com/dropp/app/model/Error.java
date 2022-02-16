package com.dropp.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {

    private int errorCode;
    private String message;
    private String description;
}
