package com.ml.hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import com.ml.hotel.model.room.Room;
import com.ml.hotel.repository.room.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    // Sample test data
    private Room sampleRoom1;
    private Room sampleRoom2;

    @BeforeEach
    public void setUp() {
        // Initialize sample rooms for testing
        sampleRoom1 = new Room();
        sampleRoom1.setId(1L);
        sampleRoom1.setSingleBeds(2);
        sampleRoom1.setDoubleBeds(1);

        sampleRoom2 = new Room();
        sampleRoom2.setId(2L);
        sampleRoom2.setSingleBeds(3);
        sampleRoom2.setDoubleBeds(2);
    }

    @Test
    public void testGetAllRooms() {
        // Arrange
        List<Room> mockRooms = new ArrayList<>();
        mockRooms.add(sampleRoom1);
        mockRooms.add(sampleRoom2);
        when(roomRepository.findAll()).thenReturn(mockRooms);

        // Act
        List<Room> result = roomService.getAllRooms();

        // Assert
        assertEquals(2, result.size());
        assertEquals(sampleRoom1, result.get(0));
        assertEquals(sampleRoom2, result.get(1));
    }

    @Test
    public void testGetRoomById_ExistingRoom() {
        // Arrange
        when(roomRepository.findById(1L)).thenReturn(Optional.of(sampleRoom1));

        // Act
        Room result = roomService.getRoomById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(sampleRoom1, result);
    }

    @Test
    public void testGetRoomById_NonExistingRoom() {
        // Arrange
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> roomService.getRoomById(99L));
    }

}
