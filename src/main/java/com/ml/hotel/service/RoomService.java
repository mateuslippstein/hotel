package com.ml.hotel.service;

import com.ml.hotel.model.Room;
import com.ml.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
    }

    public Room createRoom(Room room) {
        // You can add any additional business logic/validation before saving the room
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        Room room = getRoomById(id);

        // Update the properties of the existing room
        room.setSingleBeds(updatedRoom.getSingleBeds());
        room.setCoupleBeds(updatedRoom.getCoupleBeds());
        // Update any other properties as needed

        // Save the updated room
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}
