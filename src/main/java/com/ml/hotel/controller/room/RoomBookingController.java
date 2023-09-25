package com.ml.hotel.controller.room;

import com.ml.hotel.model.room.RoomBooking;
import com.ml.hotel.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/room-bookings")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;

    @Autowired
    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @PostMapping
    public ResponseEntity<RoomBooking> createRoomBooking(@RequestBody RoomBooking roomBooking) {
        RoomBooking createdRoomBooking = roomBookingService.createRoomBooking(roomBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateRoomBooking(@PathVariable Long id, @RequestBody RoomBooking updatedRoomBooking) {
        RoomBooking updatedBooking = roomBookingService.updateRoomBooking(id, updatedRoomBooking);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<RoomBooking>> getRoomBookingsByDate(
            @PathVariable @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate date) {
        List<RoomBooking> roomBookings = roomBookingService.getRoomBookingsByDate(date);
        if (!roomBookings.isEmpty()) {
            return ResponseEntity.ok(roomBookings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
