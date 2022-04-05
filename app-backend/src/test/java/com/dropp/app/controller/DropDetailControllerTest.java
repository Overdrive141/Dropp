package com.dropp.app.controller;

import com.dropp.app.model.DropRequest;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.service.DropDetailService;
import com.dropp.app.validation.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DropDetailControllerTest {

    @Mock
    private DropDetailService dropDetailService;
    @Mock
    private ValidationService validationService;
    @InjectMocks
    private DropDetailController controller;

    private String header = "header";

    @Test
    public void testGetDrop() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.getDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.getDrop(1l, 1l, header));
    }

    @Test
    public void testAddDropForUser() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.addDrop(anyLong(), any(DropRequest.class))).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.addDropForUser(1l, mock(DropRequest.class), header));
    }

    @Test
    public void testGetDropsForUser() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.getDropsForUser(anyLong())).thenReturn(mock(List.class));
        assertNotNull(controller.getDropsForUser(1l, header));
    }

    @Test
    public void testStarDrop() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.starDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.starDrop(1l, 1l, header));
    }

    @Test
    public void testUnstarDrop() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.unstarDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.unstarDrop(1l, 1l, header));
    }

    @Test
    public void testGetStarredDrops() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.getStarredDrops(anyLong())).thenReturn(mock(List.class));
        assertNotNull(controller.getStarredDrops(1l, header));
    }

    @Test
    public void testExploreDrop() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.exploreDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.exploreDrop(1l, 1l, header));
    }

    @Test
    public void testGetAllDrops() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.getAllDropsForUser(anyLong(), any(BigDecimal.class), any(BigDecimal.class), anyLong())).thenReturn(mock(List.class));
        assertNotNull(controller.getAllDrops(1l, BigDecimal.valueOf(12.43), BigDecimal.valueOf(2.33), 100l, header));
    }

    @Test
    public void testGetDropCountByUser() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.getDropCountByUser(any(Instant.class), any(Instant.class))).thenReturn(mock(List.class));
        assertNotNull(controller.getDropCountByUser(header));
    }

    @Test
    public void testGetExploreCountByUser() {
        doNothing().when(validationService).validate(anyString());
        when(dropDetailService.getExploreCountByUser(any(Instant.class), any(Instant.class))).thenReturn(mock(List.class));
        assertNotNull(controller.getExploreCountByUser(header));
    }
}
