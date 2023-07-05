package com.ml.hotel.service;

import com.ml.hotel.model.Room;
import com.ml.hotel.model.RoomBooking;
import com.ml.hotel.repository.RoomBookingRepository;
import com.ml.hotel.repository.RoomRepository;
import com.ml.hotel.util.RoomStatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomBookingService {

    private final RoomBookingRepository roomBookingRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomBookingService(RoomBookingRepository roomBookingRepository, RoomRepository roomRepository) {
        this.roomBookingRepository = roomBookingRepository;
        this.roomRepository = roomRepository;
    }

    public RoomBooking createRoomBooking(RoomBooking roomBooking) {
        return roomBookingRepository.save(roomBooking);
    }

    public RoomBooking updateRoomBooking(Long id, RoomBooking updatedRoomBooking) {
        RoomBooking roomBooking = roomBookingRepository.findById(id).orElse(null);
        if (roomBooking != null) {
            roomBooking.setPerson(updatedRoomBooking.getPerson());
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
