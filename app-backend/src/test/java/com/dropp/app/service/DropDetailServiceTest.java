package com.dropp.app.service;

import com.dropp.app.exception.DropNotStarredException;
import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.*;
import com.dropp.app.model.dto.Drop;
import com.dropp.app.model.dto.DropCountDTO;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.model.enums.Avatar;
import com.dropp.app.model.enums.EntityType;
import com.dropp.app.repository.DropDetailRepository;
import com.dropp.app.repository.ExploredDropRepository;
import com.dropp.app.repository.StarredDropRepository;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.DropDetailTransformer;
import com.dropp.app.transformer.UserDetailTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DropDetailServiceTest {

    @Mock
    private DropDetailRepository dropDetailRepository;
    @Mock
    private DropDetailTransformer dropDetailTransformer;
    @Mock
    private UserDetailTransformer userDetailTransformer;
    @Mock
    private UserDetailRepository userDetailRepository;
    @Mock
    private StarredDropRepository starredDropRepository;
    @Mock
    private ExploredDropRepository exploredDropRepository;
    @Mock
    private GeometryFactory geometryFactory;
    @InjectMocks
    private DropDetailService service;
    private DropDetail dropDetail1, dropDetail2, dropDetail3;
    private DropRequest dropRequest;
    private DropDetailDTO dropDetailDTO1, dropDetailDTO2;
    private UserDetail userDetail;
    private UserDetailDTO userDetailDTO;
    private Point point;
    private GeometryFactory factory;

    @BeforeEach
    public void setup() {
        factory = new GeometryFactory();
        point = factory.createPoint(new Coordinate(12.34, 43.4));
        userDetail = UserDetail.builder()
                .id(1l)
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .favDrops(0l)
                .avatar(Avatar.AV1)
                .build();
        userDetailDTO = UserDetailDTO.builder()
                .id(1l)
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .favDrops(0l)
                .avatar(Avatar.AV1)
                .build();
        dropDetail1 = DropDetail.builder()
                .id(1L)
                .user(userDetail)
                .type(EntityType.TEXT)
                .message("Hello!")
                .coordinate(point)
                .build();
        dropDetail2 = DropDetail.builder()
                .id(2l)
                .user(userDetail)
                .type(EntityType.TEXT)
                .message("Hello!")
                .coordinate(point)
                .build();
        dropDetail3 = DropDetail.builder()
                .user(userDetail)
                .type(EntityType.TEXT)
                .message("Hello!")
                .coordinate(point)
                .build();
        dropDetailDTO1 = DropDetailDTO.builder()
                .id(1L)
                .userId(1l)
                .type(EntityType.TEXT)
                .message("Hello!")
                .latitude(BigDecimal.valueOf(43.4))
                .longitude(BigDecimal.valueOf(12.34))
                .build();
        dropDetailDTO2 = DropDetailDTO.builder()
                .id(2L)
                .userId(1l)
                .type(EntityType.TEXT)
                .message("Hello!")
                .latitude(BigDecimal.valueOf(43.4))
                .longitude(BigDecimal.valueOf(12.34))
                .build();
        dropRequest = DropRequest.builder()
                .type(EntityType.TEXT)
                .message("Hello!")
                .longitude(new BigDecimal(12.34))
                .latitude(new BigDecimal(43.4))
                .build();
    }

    @Test
    public void testGetDrop1() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(dropDetailTransformer.map(eq(dropDetail1))).thenReturn(dropDetailDTO1);
        DropDetailDTO actualDropDetailDTO = service.getDrop(1l, 1l);
        assertEquals(1l, actualDropDetailDTO.getId());
    }

    @Test
    public void testGetDrop2() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.getDrop(1l, 1l));
    }

    @Test
    public void testGetDrop3() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.getDrop(1l, 1l));
    }

    @Test
    public void testGetDropsForUser1() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findByUser(eq(userDetail))).thenReturn(List.of(dropDetail1, dropDetail2));
        when(dropDetailTransformer.map(eq(dropDetail1))).thenReturn(dropDetailDTO1);
        when(dropDetailTransformer.map(eq(dropDetail2))).thenReturn(dropDetailDTO2);
        List<DropDetailDTO> list = service.getDropsForUser(1l);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(dropDetail1.getId(), list.get(0).getId());
        assertEquals(dropDetail1.getUser().getId(), list.get(0).getUserId());
        assertEquals(dropDetail1.getType(), list.get(0).getType());
        assertEquals(dropDetail1.getMessage(), list.get(0).getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(list.get(0)
                .getLongitude().doubleValue(), list.get(0).getLatitude().doubleValue())));
        assertEquals(dropDetail2.getId(), list.get(1).getId());
        assertEquals(dropDetail2.getUser().getId(), list.get(1).getUserId());
        assertEquals(dropDetail2.getType(), list.get(1).getType());
        assertEquals(dropDetail2.getMessage(), list.get(1).getMessage());
        assertEquals(dropDetail2.getCoordinate(), factory.createPoint(new Coordinate(list.get(1)
                .getLongitude().doubleValue(), list.get(1).getLatitude().doubleValue())));
    }

    @Test
    public void testGetDropsForUser2() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.getDropsForUser(1l));
    }

    @Test
    public void testAddDrop1() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailTransformer.map(eq(dropRequest))).thenReturn(dropDetail3);
        when(dropDetailRepository.save(eq(dropDetail3))).thenReturn(dropDetail1);
        when(dropDetailTransformer.map(eq(dropDetail1))).thenReturn(dropDetailDTO1);
        DropDetailDTO dropDetailDTO = service.addDrop(1l, dropRequest);
        assertNotNull(dropDetailDTO);
        assertEquals(dropDetail1.getId(), dropDetailDTO1.getId());
        assertEquals(dropDetail1.getUser().getId(), dropDetailDTO1.getUserId());
        assertEquals(dropDetail1.getType(), dropDetailDTO1.getType());
        assertEquals(dropDetail1.getMessage(), dropDetailDTO1.getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(dropDetailDTO
                .getLongitude().doubleValue(), dropDetailDTO1.getLatitude().doubleValue())));
    }

    @Test
    public void testAddDrop2() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.addDrop(1l, dropRequest));
    }

    @Test
    public void testStarDrop1() {
        when(userDetailRepository.findById(eq(1l))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(starredDropRepository.findByUserDetailAndDropDetail(eq(userDetail), eq(dropDetail1)))
                .thenReturn(Optional.empty());
        when(starredDropRepository.save(any(StarredDrop.class))).thenReturn(mock(StarredDrop.class));
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(mock(UserDetail.class));
        when(dropDetailTransformer.map(dropDetail1)).thenReturn(dropDetailDTO1);
        DropDetailDTO dropDetailDTO = service.starDrop(1l, 1l);
        assertNotNull(dropDetailDTO);
        assertEquals(dropDetail1.getId(), dropDetailDTO.getId());
        assertEquals(dropDetail1.getUser().getId(), dropDetailDTO.getUserId());
        assertEquals(dropDetail1.getType(), dropDetailDTO.getType());
        assertEquals(dropDetail1.getMessage(), dropDetailDTO.getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(dropDetailDTO
                .getLongitude().doubleValue(), dropDetailDTO1.getLatitude().doubleValue())));
    }

    @Test
    public void testStarDrop2() {
        StarredDrop starredDrop = StarredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail1)
                .isActive(false)
                .build();
        when(userDetailRepository.findById(eq(1l))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(starredDropRepository.findByUserDetailAndDropDetail(eq(userDetail), eq(dropDetail1)))
                .thenReturn(Optional.of(starredDrop));
        when(starredDropRepository.save(any(StarredDrop.class))).thenReturn(mock(StarredDrop.class));
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(mock(UserDetail.class));
        when(dropDetailTransformer.map(dropDetail1)).thenReturn(dropDetailDTO1);
        DropDetailDTO dropDetailDTO = service.starDrop(1l, 1l);
        assertNotNull(dropDetailDTO);
        assertEquals(dropDetail1.getId(), dropDetailDTO.getId());
        assertEquals(dropDetail1.getUser().getId(), dropDetailDTO.getUserId());
        assertEquals(dropDetail1.getType(), dropDetailDTO.getType());
        assertEquals(dropDetail1.getMessage(), dropDetailDTO.getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(dropDetailDTO
                .getLongitude().doubleValue(), dropDetailDTO1.getLatitude().doubleValue())));
    }

    @Test
    public void testStarDrop3() {
        when(userDetailRepository.findById(eq(1l))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(starredDropRepository.findByUserDetailAndDropDetail(eq(userDetail), eq(dropDetail1)))
                .thenReturn(Optional.empty());
        when(starredDropRepository.save(any(StarredDrop.class))).thenReturn(mock(StarredDrop.class));
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(mock(UserDetail.class));
        when(dropDetailTransformer.map(dropDetail1)).thenReturn(dropDetailDTO1);
        DropDetailDTO dropDetailDTO = service.starDrop(1l, 1l);
        assertNotNull(dropDetailDTO);
        assertEquals(dropDetail1.getId(), dropDetailDTO.getId());
        assertEquals(dropDetail1.getUser().getId(), dropDetailDTO.getUserId());
        assertEquals(dropDetail1.getType(), dropDetailDTO.getType());
        assertEquals(dropDetail1.getMessage(), dropDetailDTO.getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(dropDetailDTO
                .getLongitude().doubleValue(), dropDetailDTO1.getLatitude().doubleValue())));
    }

    @Test
    public void testStarDrop4() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.starDrop(1l, 1l));
    }

    @Test
    public void testStarDrop5() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.starDrop(1l, 1l));
    }

    @Test
    public void testUnstarDrop1() {
        StarredDrop starredDrop = StarredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail1)
                .isActive(true)
                .build();
        when(userDetailRepository.findById(eq(1l))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(starredDropRepository.findByUserDetailAndDropDetail(eq(userDetail), eq(dropDetail1)))
                .thenReturn(Optional.of(starredDrop));
        when(starredDropRepository.save(any(StarredDrop.class))).thenReturn(mock(StarredDrop.class));
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(mock(UserDetail.class));
        when(dropDetailTransformer.map(dropDetail1)).thenReturn(dropDetailDTO1);
        DropDetailDTO dropDetailDTO = service.unstarDrop(1l, 1l);
        assertNotNull(dropDetailDTO);
        assertEquals(dropDetail1.getId(), dropDetailDTO.getId());
        assertEquals(dropDetail1.getUser().getId(), dropDetailDTO.getUserId());
        assertEquals(dropDetail1.getType(), dropDetailDTO.getType());
        assertEquals(dropDetail1.getMessage(), dropDetailDTO.getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(dropDetailDTO
                .getLongitude().doubleValue(), dropDetailDTO1.getLatitude().doubleValue())));
    }

    @Test
    public void testUnstarDrop2() {
        when(userDetailRepository.findById(eq(1l))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(starredDropRepository.findByUserDetailAndDropDetail(eq(userDetail), eq(dropDetail1)))
                .thenReturn(Optional.empty());
        assertThrows(DropNotStarredException.class, () -> service.unstarDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
        verify(dropDetailRepository, times(1)).findById(eq(1l));
        verify(starredDropRepository, times(1)).findByUserDetailAndDropDetail(eq(userDetail), eq(dropDetail1));
    }

    @Test
    public void testUnstarDrop3() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.unstarDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
    }

    @Test
    public void testUnstarDrop4() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.unstarDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
        verify(dropDetailRepository, times(1)).findById(eq(1l));
    }

    @Test
    public void testGetStarredDrop1() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        StarredDrop starredDrop = StarredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail1)
                .isActive(true)
                .build();
        when(starredDropRepository.findByUserDetailAndIsActive(eq(userDetail), eq(true))).thenReturn(List.of(starredDrop));
        when(dropDetailTransformer.map(eq(dropDetail1))).thenReturn(dropDetailDTO1);
        List<DropDetailDTO> list = service.getStarredDrops(1l);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(dropDetail1.getId(), list.get(0).getId());
        assertEquals(dropDetail1.getUser().getId(), list.get(0).getUserId());
        assertEquals(dropDetail1.getType(), list.get(0).getType());
        assertEquals(dropDetail1.getMessage(), list.get(0).getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(list.get(0)
                .getLongitude().doubleValue(), list.get(0).getLatitude().doubleValue())));
        verify(userDetailRepository, times(1)).findById(eq(1l));
        verify(starredDropRepository, times(1)).findByUserDetailAndIsActive(eq(userDetail), eq(true));
        verify(dropDetailTransformer, times(1)).map(eq(dropDetail1));
    }

    @Test
    public void testGetStarredDrop2() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.unstarDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
    }

    @Test
    public void testExploreDrop1() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenReturn(Optional.of(dropDetail1));
        when(exploredDropRepository.save(any(ExploredDrop.class))).thenReturn(mock(ExploredDrop.class));
        when(dropDetailTransformer.map(eq(dropDetail1))).thenReturn(dropDetailDTO1);
        DropDetailDTO dropDetailDTO = service.exploreDrop(1l, 1l);
        assertNotNull(dropDetailDTO);
        assertEquals(dropDetail1.getId(), dropDetailDTO1.getId());
        assertEquals(dropDetail1.getUser().getId(), dropDetailDTO1.getUserId());
        assertEquals(dropDetail1.getType(), dropDetailDTO1.getType());
        assertEquals(dropDetail1.getMessage(), dropDetailDTO1.getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(dropDetailDTO
                .getLongitude().doubleValue(), dropDetailDTO1.getLatitude().doubleValue())));
    }

    @Test
    public void testExploreDrop2() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.exploreDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
    }

    @Test
    public void testExploreDrop3() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findById(eq(1L))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.exploreDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
        verify(dropDetailRepository, times(1)).findById(eq(1l));
    }

    @Test
    public void testGetAllDropsForUser1() {
        long userId = 1, radius = 100;
        BigDecimal latitude = BigDecimal.valueOf(43.3), longitude = BigDecimal.valueOf(12.34);
        Point point = factory.createPoint(new Coordinate(longitude.doubleValue(), latitude.doubleValue()));
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenReturn(Optional.of(userDetail));
        when(dropDetailRepository.findAllDrops(eq(point), eq(radius))).thenReturn(List.of(dropDetail1, dropDetail2));
        when(geometryFactory.createPoint(any(Coordinate.class))).thenReturn(point);
        when(exploredDropRepository.findByUserDetailAndDropDetail(eq(userDetail), any(DropDetail.class)))
                .thenReturn(Optional.of(getTestExploredDrop(dropDetail1, true)))
                .thenReturn(Optional.of(getTestExploredDrop(dropDetail2, false)));
        when(dropDetailTransformer.map(any(DropDetail.class))).thenReturn(dropDetailDTO1).thenReturn(dropDetailDTO2);
        List<Drop> drops = service.getAllDropsForUser(userId, latitude, longitude, radius);
        assertNotNull(drops);
        assertEquals(2, drops.size());
        assertEquals(dropDetail1.getId(), drops.get(0).getDropDetail().getId());
        assertEquals(dropDetail1.getUser().getId(), drops.get(0).getDropDetail().getUserId());
        assertEquals(dropDetail1.getType(), drops.get(0).getDropDetail().getType());
        assertEquals(dropDetail1.getMessage(), drops.get(0).getDropDetail().getMessage());
        assertEquals(dropDetail1.getCoordinate(), factory.createPoint(new Coordinate(drops.get(0).getDropDetail()
                .getLongitude().doubleValue(), drops.get(0).getDropDetail().getLatitude().doubleValue())));
        assertEquals(true, drops.get(0).isSeen());
        assertEquals(dropDetail2.getId(), drops.get(1).getDropDetail().getId());
        assertEquals(dropDetail2.getUser().getId(), drops.get(1).getDropDetail().getUserId());
        assertEquals(dropDetail2.getType(), drops.get(1).getDropDetail().getType());
        assertEquals(dropDetail2.getMessage(), drops.get(1).getDropDetail().getMessage());
        assertEquals(dropDetail2.getCoordinate(), factory.createPoint(new Coordinate(drops.get(1).getDropDetail()
                .getLongitude().doubleValue(), drops.get(1).getDropDetail().getLatitude().doubleValue())));
        assertEquals(false, drops.get(1).isSeen());
        verify(userDetailRepository, times(1)).findById(eq(userId));
        verify(dropDetailRepository, times(1)).findAllDrops(eq(point), eq(radius));
        verify(dropDetailTransformer, times(2)).map(any(DropDetail.class));
    }

    @Test
    public void testGetAllDropsForUser2() {
        when(userDetailRepository.findById(eq(userDetail.getId()))).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> service.exploreDrop(1l, 1l));
        verify(userDetailRepository, times(1)).findById(eq(1l));
    }

    @Test
    public void testGetDropCountByUser() {
        DropCount dropCount = DropCount.builder()
                .user(userDetail)
                .count(1l)
                .build();
        when(dropDetailRepository.findDropCountByUser(any(Instant.class), any(Instant.class))).thenReturn(List.of(dropCount));
        when(userDetailTransformer.map(eq(userDetail))).thenReturn(userDetailDTO);
        List<DropCountDTO> list = service.getDropCountByUser(Instant.now(), Instant.now());
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(userDetailDTO, list.get(0).getUser());
        assertEquals(1, list.get(0).getCount());
        verify(dropDetailRepository, times(1)).findDropCountByUser(any(Instant.class), any(Instant.class));
    }

    @Test
    public void testGetExploreCountByUser() {
        DropCount dropCount = DropCount.builder()
                .user(userDetail)
                .count(1l)
                .build();
        when(exploredDropRepository.findExploreCountByUser(any(Instant.class), any(Instant.class))).thenReturn(List.of(dropCount));
        when(userDetailTransformer.map(eq(userDetail))).thenReturn(userDetailDTO);
        List<DropCountDTO> list = service.getExploreCountByUser(Instant.now(), Instant.now());
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(userDetailDTO, list.get(0).getUser());
        assertEquals(1, list.get(0).getCount());
        verify(exploredDropRepository, times(1)).findExploreCountByUser(any(Instant.class), any(Instant.class));
    }

    private ExploredDrop getTestExploredDrop(DropDetail dropDetail, boolean isSeen) {
        return ExploredDrop.builder()
                .userDetail(userDetail)
                .dropDetail(dropDetail)
                .isSeen(isSeen)
                .build();
    }

}
