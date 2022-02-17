package com.dropp.app.controller;

import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.service.DropDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DropDetailController {

    private final DropDetailService service;

    @GetMapping("/drop/{id}")
    public DropDetail getDrop(@PathVariable Long id) {
        return service.getDrop(id);
    }

    @PostMapping("/user/{userId}/drop")
    public DropDetail addDropForUser(@PathVariable @NotNull @NotEmpty Long userId, @RequestBody @Valid DropRequest dropRequest) {
        return service.addDrop(userId, dropRequest);
    }

    @GetMapping("/user/{userId}/drop")
    public List<DropDetail> getDropsForUser(@PathVariable @NotNull @NotEmpty Long userId) {
        return service.getDropsForUser(userId);
    }
}
