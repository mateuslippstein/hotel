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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Creates a new room booking in the database after validating the provided data.
     *
     * @param newRoomBooking The room booking object to be created.
     * @return The created room booking.
     * @throws IllegalArgumentException if the room ID, person ID, or date is not provided, or if
     *                                  a booking already exists for the given date and room.
     * @throws EntityNotFoundException   if the room or person with the provided IDs does not exist in the database.
     */
    public RoomBooking createRoomBooking(RoomBooking newRoomBooking) {
        Long roomId = newRoomBooking.getRoom().getId();
        Long personId = newRoomBooking.getPerson().getId();

        if (roomId == null || personId == null) {
            throw new IllegalArgumentException("Room ID and Person ID must be provided");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + personId));

        LocalDate date = newRoomBooking.getDate();
        List<RoomBooking> existingRoomBookings = roomBookingRepository.findByDateAndRoom(date, room);
        if (!existingRoomBookings.isEmpty()) {
            throw new IllegalArgumentException("A Room Booking already exists for the given date and room");
        }

        newRoomBooking.setRoom(room);
        newRoomBooking.setPerson(person);

        return roomBookingRepository.save(newRoomBooking);
    }

    /**
     * Updates an existing room booking in the database.
     *
     * @param id             The ID of the room booking to be updated.
     * @param updatedRoomBooking The updated room booking information.
     * @return The updated room booking if it exists, otherwise null.
     * @throws IllegalArgumentException if the person ID is not provided.
     * @throws EntityNotFoundException   if the room booking or person with the provided ID does not exist in the database.
     */
    public RoomBooking updateRoomBooking(Long id, RoomBooking updatedRoomBooking) {
        RoomBooking roomBooking = roomBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RoomBooking not found with id: " + id));
        if (roomBooking != null) {
            Long personId = updatedRoomBooking.getPerson().getId();

            if (personId == null) {
                throw new IllegalArgumentException("Person ID must be provided");
            }

            Person person = personRepository.findById(personId)
                    .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + personId));

            roomBooking.setPerson(person);
            roomBooking.setStatus(updatedRoomBooking.getStatus());
            return roomBookingRepository.save(roomBooking);
        }
        return null;
    }

    /**
     * Retrieves a list of room bookings for a specific date, including dummy room bookings
     * for rooms without existing bookings on that date.
     *
     * @param date The date for which to retrieve room bookings.
     * @return A list of room bookings for the specified date, including dummy bookings for rooms without existing bookings.
     */
    public List<RoomBooking> getRoomBookingsByDate(LocalDate date) {
        List<RoomBooking> roomBookings = roomBookingRepository.findByDate(date);
        List<Room> rooms = roomRepository.findAll();

        Map<Long, RoomBooking> roomBookingMap = roomBookings.stream()
                .collect(Collectors.toMap(rb -> rb.getRoom().getId(), rb -> rb));

        List<RoomBooking> updatedRoomBookings = new ArrayList<>();

        for (Room room : rooms) {
            if (roomBookingMap.containsKey(room.getId())) {
                updatedRoomBookings.add(roomBookingMap.get(room.getId()));
            } else {
                RoomBooking dummyRoomBooking = new RoomBooking(null, date, room, null, RoomStatusEnum.AVAILABLE, null,
                        null);

                updatedRoomBookings.add(dummyRoomBooking);
            }
        }

        return updatedRoomBookings;
    }

}
