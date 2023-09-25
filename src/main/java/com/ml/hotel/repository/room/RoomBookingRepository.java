package com.ml.hotel.repository.room;

import com.ml.hotel.model.room.Room;
import com.ml.hotel.model.room.RoomBooking;
import com.ml.hotel.util.RoomStatusEnum;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findByDate(LocalDate date);
    List<RoomBooking> findByDateAndRoom(LocalDate date, Room room);
    List<RoomBooking> findByRoomIdAndStatus(Long roomId, RoomStatusEnum status);
    List<RoomBooking> findByPersonIdAndStatus(Long personId, RoomStatusEnum status);
}
