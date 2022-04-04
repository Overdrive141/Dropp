package com.dropp.app.controller;

import com.dropp.app.model.DropRequest;
import com.dropp.app.model.dto.Drop;
import com.dropp.app.model.dto.DropCountDTO;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.service.DropDetailService;
import com.dropp.app.validation.ValidationService;
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

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{userId}/drop/{id}")
    public DropDetailDTO getDrop(@PathVariable Long userId, @PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.getDrop(userId, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user/{userId}/drop")
    public DropDetailDTO addDropForUser(@PathVariable @NotNull Long userId, @RequestBody @Valid DropRequest dropRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.addDrop(userId, dropRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{userId}/drop")
    public List<DropDetailDTO> getDropsForUser(@PathVariable @NotNull @NotEmpty Long userId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.getDropsForUser(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user/{userId}/drop/{dropId}/star")
    public DropDetailDTO starDrop(@PathVariable @NotNull @NotEmpty Long userId, @PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.starDrop(userId, dropId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping("/user/{userId}/drop/{dropId}/star")
    public DropDetailDTO unstarDrop(@PathVariable @NotNull @NotEmpty Long userId, @PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.unstarDrop(userId, dropId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{userId}/drop/star")
    public List<DropDetailDTO> getStarredDrops(@PathVariable @NotNull @NotEmpty Long userId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.getStarredDrops(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user/{userId}/drop/{dropId}/explore")
    public DropDetailDTO exploreDrop(@PathVariable @NotNull @NotEmpty Long userId, @PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.exploreDrop(userId, dropId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{userId}/drop/all")
    public List<Drop> getAllDrops(@PathVariable @NotNull Long userId, @RequestParam("lat") BigDecimal latitude, @RequestParam("lng") BigDecimal longitude, @RequestParam("radius") Long radius, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return dropDetailService.getAllDropsForUser(userId, latitude, longitude, radius);
    }

    @GetMapping("/drop/score/drop")
    public List<DropCountDTO> getDropCountByUser(@NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.CANADA).getFirstDayOfWeek();
        LocalDateTime startDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime endDateTime = LocalDate.now().atTime(LocalTime.MAX);
        return dropDetailService.getDropCountByUser(startDateTime.toInstant(ZoneOffset.UTC), endDateTime.toInstant(ZoneOffset.UTC));
    }

    @GetMapping("/drop/score/explore")
    public List<DropCountDTO> getExploreCountByUser(@NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.CANADA).getFirstDayOfWeek();
        LocalDateTime startDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime endDateTime = LocalDate.now().atTime(LocalTime.MAX);
        return dropDetailService.getExploreCountByUser(startDateTime.toInstant(ZoneOffset.UTC), endDateTime.toInstant(ZoneOffset.UTC));
    }
}
