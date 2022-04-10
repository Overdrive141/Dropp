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
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.getDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.getDrop(1l, header));
    }

    @Test
    public void testAddDropForUser() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.addDrop(anyLong(), any(DropRequest.class))).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.addDropForUser(mock(DropRequest.class), header));
    }

    @Test
    public void testGetDropsForUser() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.getDropsForUser(anyLong())).thenReturn(mock(List.class));
        assertNotNull(controller.getDropsForUser(header));
    }

    @Test
    public void testStarDrop() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.starDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.starDrop(1l, header));
    }

    @Test
    public void testUnstarDrop() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.unstarDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.unstarDrop(1l, header));
    }

    @Test
    public void testGetStarredDrops() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.getStarredDrops(anyLong())).thenReturn(mock(List.class));
        assertNotNull(controller.getStarredDrops(header));
    }

    @Test
    public void testExploreDrop() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.exploreDrop(anyLong(), anyLong())).thenReturn(mock(DropDetailDTO.class));
        assertNotNull(controller.exploreDrop(1l, header));
    }

    @Test
    public void testGetAllDrops() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.getAllDropsForUser(anyLong(), any(BigDecimal.class), any(BigDecimal.class), anyLong())).thenReturn(mock(List.class));
        assertNotNull(controller.getAllDrops(BigDecimal.valueOf(12.43), BigDecimal.valueOf(2.33), 100l, header));
    }

    @Test
    public void testGetDropCountByUser() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.getDropCountByUser(any(Instant.class), any(Instant.class))).thenReturn(mock(List.class));
        assertNotNull(controller.getDropCountByUser(header));
    }

    @Test
    public void testGetExploreCountByUser() {
        when(validationService.validate(anyString())).thenReturn(1l);
        when(dropDetailService.getExploreCountByUser(any(Instant.class), any(Instant.class))).thenReturn(mock(List.class));
        assertNotNull(controller.getExploreCountByUser(header));
    }
}
