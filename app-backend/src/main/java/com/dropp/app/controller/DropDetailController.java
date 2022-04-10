package com.dropp.app.controller;

import com.dropp.app.model.DropRequest;
import com.dropp.app.model.Error;
import com.dropp.app.model.dto.Drop;
import com.dropp.app.model.dto.DropCountDTO;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.service.DropDetailService;
import com.dropp.app.validation.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DropDetailController {

    private final DropDetailService dropDetailService;
    private final ValidationService validationService;

    @Operation(summary = "Get drop",
            description = "Get drop",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drop Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DropDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/drop/{id}")
    public DropDetailDTO getDrop(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.getDrop(userId, id);
    }

    @Operation(summary = "Add new drop",
            description = "Add new drop",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DropRequest.class))}),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drop Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DropDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user/drop")
    public DropDetailDTO addDropForUser(@RequestBody @Valid DropRequest dropRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.addDrop(userId, dropRequest);
    }

    @Operation(summary = "Get all drops of a user",
            description = "Get all drops of a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of Drop Details", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DropDetailDTO.class)))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/drop")
    public List<DropDetailDTO> getDropsForUser(@NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.getDropsForUser(userId);
    }

    @Operation(summary = "Star a drop",
            description = "Star a drop",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drop Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DropDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user/drop/{dropId}/star")
    public DropDetailDTO starDrop(@PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.starDrop(userId, dropId);
    }

    @Operation(summary = "Unstar a drop",
            description = "Unstar a drop",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drop Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DropDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping("/user/drop/{dropId}/star")
    public DropDetailDTO unstarDrop(@PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.unstarDrop(userId, dropId);
    }

    @Operation(summary = "Get all the starred drops by a user",
            description = "Get all the starred drops by a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of Drop Details", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DropDetailDTO.class)))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/drop/star")
    public List<DropDetailDTO> getStarredDrops(@NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.getStarredDrops(userId);
    }

    @Operation(summary = "Explore a Drop",
            description = "Explore a drop",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drop Details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DropDetailDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user/drop/{dropId}/explore")
    public DropDetailDTO exploreDrop(@PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.exploreDrop(userId, dropId);
    }

    @Operation(summary = "Get new drops for a user",
            description = "Get new drops for a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of Drops", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Drop.class)))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/drop/all")
    public List<Drop> getAllDrops(@RequestParam("lat") BigDecimal latitude, @RequestParam("lng") BigDecimal longitude, @RequestParam("radius") Long radius, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        long userId = validationService.validate(authorizationHeader);
        return dropDetailService.getAllDropsForUser(userId, latitude, longitude, radius);
    }

    @Operation(summary = "Get Drop Count for leaderboard",
            description = "Get Drop Count for leader board",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of Drop Count", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DropCountDTO.class)))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            }
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/drop/score/drop")
    public List<DropCountDTO> getDropCountByUser(@NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.CANADA).getFirstDayOfWeek();
        LocalDateTime startDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime endDateTime = LocalDate.now().atTime(LocalTime.MAX);
        return dropDetailService.getDropCountByUser(startDateTime.toInstant(ZoneOffset.UTC), endDateTime.toInstant(ZoneOffset.UTC));
    }

    @Operation(summary = "Get Explore Count for leaderboard",
            description = "Get Explore Count for leaderboard",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of Explore Count", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DropCountDTO.class)))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})
            })
    @GetMapping("/drop/score/explore")
    public List<DropCountDTO> getExploreCountByUser(@NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.CANADA).getFirstDayOfWeek();
        LocalDateTime startDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime endDateTime = LocalDate.now().atTime(LocalTime.MAX);
        return dropDetailService.getExploreCountByUser(startDateTime.toInstant(ZoneOffset.UTC), endDateTime.toInstant(ZoneOffset.UTC));
    }
}
