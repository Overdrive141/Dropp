package com.dropp.app.controller;

import com.dropp.app.model.Error;
import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.service.UserDetailService;
import com.dropp.app.validation.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;
    private final ValidationService validationService;


    @Operation(summary = "Get User Details from Email or Username",
            description = "Get User Details from Email or Username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{emailOrUsername}")
    public UserDetailDTO getUserByEmailIdOrUsername(@PathVariable("emailOrUsername") String emailOrUsername, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return userDetailService.getUserByEmailOrUsername(emailOrUsername);
    }

    @Operation(summary = "Add new user",
            description = "Add new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class))}),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user")
    public UserDetailDTO addUser(@Valid @RequestBody UserRequest userRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return userDetailService.addUser(userRequest);
    }
}
