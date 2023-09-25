package com.ml.hotel.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ml.hotel.model.room.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
