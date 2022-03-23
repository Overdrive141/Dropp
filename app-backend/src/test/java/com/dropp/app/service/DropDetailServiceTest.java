//package com.dropp.app.service;
//
//import com.dropp.app.exception.ResourceNotFoundException;
//import com.dropp.app.exception.UserNotFoundException;
//import com.dropp.app.model.DropDetail;
//import com.dropp.app.model.DropRequest;
//import com.dropp.app.model.enums.EntityType;
//import com.dropp.app.repository.DropDetailRepository;
//import com.dropp.app.repository.UserDetailRepository;
//import com.dropp.app.transformer.DropDetailTransformer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class DropDetailServiceTest {
//
//    @Mock
//    private DropDetailRepository dropDetailRepository;
//    @Mock
//    private DropDetailTransformer transformer;
//    @Mock
//    private UserDetailRepository userDetailRepository;
//    @InjectMocks
//    private DropDetailService service;
//    private DropDetail dropDetail1, dropDetail2;
//    private DropRequest dropRequest;
//
//    @BeforeEach
//    public void setup() {
//        dropDetail1 = DropDetail.builder()
//                .id(1L)
//                .userId(1L)
//                .type(EntityType.TEXT)
//                .message("Hello!")
//                .longitude(new BigDecimal(83.4))
//                .latitude(new BigDecimal(2.34))
//                .build();
//        dropDetail2 = DropDetail.builder()
//                .type(EntityType.TEXT)
//                .message("Hello!")
//                .longitude(new BigDecimal(83.4))
//                .latitude(new BigDecimal(2.34))
//                .build();
//        dropRequest = DropRequest.builder()
//                .type(EntityType.TEXT)
//                .message("Hello!")
//                .longitude(new BigDecimal(83.4))
//                .latitude(new BigDecimal(2.34))
//                .build();
//    }
//
//    @Test
//    public void testGetDrop1() {
//        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
//        DropDetail drop = service.getDrop(1L);
//        assertNotNull(drop);
//        assertEquals(dropDetail1.getId(), drop.getId());
//        assertEquals(dropDetail1.getUserId(), drop.getUserId());
//        assertEquals(dropDetail1.getMessage(), drop.getMessage());
//        assertEquals(dropDetail1.getType(), drop.getType());
//        assertEquals(dropDetail1.getLatitude(), drop.getLatitude());
//        assertEquals(dropDetail1.getLongitude(), drop.getLongitude());
//    }
//
//    @Test
//    public void testGetDrop2() {
//        when(dropDetailRepository.findById(eq(2L))).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> service.getDrop(2L));
//    }
//
//    @Test
//    public void testGetDropsForUser1() {
//        when(userDetailRepository.existsById(1L)).thenReturn(true);
//        when(dropDetailRepository.findByUserId(1L)).thenReturn(List.of(mock(DropDetail.class), mock(DropDetail.class)));
//        List<DropDetail> list = service.getDropsForUser(1L);
//        assertNotNull(list);
//        assertEquals(2, list.size());
//    }
//
//    @Test
//    public void testGetDropsForUser2() {
//        when(userDetailRepository.existsById(1L)).thenReturn(false);
//        assertThrows(UserNotFoundException.class, () -> service.getDropsForUser(1L));
//    }
//
//    @Test
//    public void testAddDrop() {
//        when(userDetailRepository.existsById(1L)).thenReturn(true);
//        when(transformer.map(eq(dropRequest))).thenReturn(dropDetail2);
//        when(dropDetailRepository.save(dropDetail2)).thenReturn(dropDetail1);
//        DropDetail drop = service.addDrop(1L, dropRequest);
//        assertNotNull(drop);
//        assertEquals(dropDetail1.getId(), drop.getId());
//        assertEquals(dropDetail1.getUserId(), drop.getUserId());
//        assertEquals(dropDetail1.getMessage(), drop.getMessage());
//        assertEquals(dropDetail1.getType(), drop.getType());
//        assertEquals(dropDetail1.getLatitude(), drop.getLatitude());
//        assertEquals(dropDetail1.getLongitude(), drop.getLongitude());
//    }
//}
