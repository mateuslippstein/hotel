package com.ml.hotel.service;

import com.ml.hotel.model.room.Room;
import com.ml.hotel.repository.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Retrieves all rooms.
     *
     * @return a list of all rooms.
     */
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    /**
     * Retrieves a room by its ID.
     *
     * @param id the ID of the room to retrieve.
     * @return the room with the specified ID.
     * @throws EntityNotFoundException if the room with the given ID does not exist.
     */
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
    }

    /**
     * Creates a new room.
     *
     * @param room the room to create.
     * @return the created room.
     */
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Updates an existing room.
     *
     * @param id          the ID of the room to update.
     * @param updatedRoom the updated room information.
     * @return the updated room.
     * @throws EntityNotFoundException if the room with the given ID does not exist.
     */
    public Room updateRoom(Long id, Room updatedRoom) {
        Room room = getRoomById(id);

        room.setSingleBeds(updatedRoom.getSingleBeds());
        room.setDoubleBeds(updatedRoom.getDoubleBeds());

        return roomRepository.save(room);
    }

    
    public Room deleteRoom(Long id) {
        Room room = getRoomById(id);
        room.setIsDeleted(true);
        return roomRepository.save(room);
    }
}
