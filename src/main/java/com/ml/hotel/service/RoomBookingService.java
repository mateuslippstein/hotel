package com.ml.hotel.service;

import com.ml.hotel.model.Person;
import com.ml.hotel.model.Room;
import com.ml.hotel.model.RoomBooking;
import com.ml.hotel.repository.PersonRepository;
import com.ml.hotel.repository.RoomBookingRepository;
import com.ml.hotel.repository.RoomRepository;
import com.ml.hotel.util.RoomStatusEnum;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomBookingService {

    private final RoomBookingRepository roomBookingRepository;
    private final RoomRepository roomRepository;
    private final PersonRepository personRepository;

    @Autowired
    public RoomBookingService(RoomBookingRepository roomBookingRepository, RoomRepository roomRepository,
            PersonRepository personRepository) {
        this.roomBookingRepository = roomBookingRepository;
        this.roomRepository = roomRepository;
        this.personRepository = personRepository;
    }

    public RoomBooking createRoomBooking(RoomBooking roomBooking) {
        Long roomId = roomBooking.getRoom().getId();
        Long personId = roomBooking.getPerson().getId();

        if (roomId == null || personId == null) {
            throw new IllegalArgumentException("Room ID and Person ID must be provided");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + personId));

        // Check if a Room Booking already exists for the given date and room
        LocalDate date = roomBooking.getDate();
        List<RoomBooking> existingRoomBookings = roomBookingRepository.findByDateAndRoom(date, room);
        if (!existingRoomBookings.isEmpty()) {
            throw new IllegalArgumentException("A Room Booking already exists for the given date and room");
        }

        roomBooking.setRoom(room);
        roomBooking.setPerson(person);

        return roomBookingRepository.save(roomBooking);
    }

    public RoomBooking updateRoomBooking(Long id, RoomBooking updatedRoomBooking) {
        RoomBooking roomBooking = roomBookingRepository.findById(id).orElse(null);
        if (roomBooking != null) {
            Long personId = updatedRoomBooking.getPerson().getId();

            if (personId == null) {
                throw new IllegalArgumentException("Room ID and Person ID must be provided");
            }

            Person person = personRepository.findById(personId)
                    .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + personId));

            roomBooking.setPerson(person);
            roomBooking.setStatus(updatedRoomBooking.getStatus());
            return roomBookingRepository.save(roomBooking);
        }
        return null;
    }

    public List<RoomBooking> getRoomBookingsByDate(LocalDate date) {
        List<RoomBooking> roomBookings = roomBookingRepository.findByDate(date);
        if (roomBookings.isEmpty()) {
            List<Room> rooms = roomRepository.findAll();

            for (Room room : rooms) {
                RoomBooking roomBooking = new RoomBooking();
                roomBooking.setDate(date);
                roomBooking.setRoom(room);
                roomBooking.setPerson(null);
                roomBooking.setStatus(RoomStatusEnum.AVAILABLE);

                roomBookings.add(roomBooking);
            }
        }

        return roomBookings;
    }

}
