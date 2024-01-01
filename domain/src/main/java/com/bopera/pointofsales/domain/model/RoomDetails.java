package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomDetails {
    private Long id;
    private String name;
    private int sorting;
    private List<HallTableDetails> hallTableDetails;

    public static RoomDetails mapFromRoom(Room room) {
        return RoomDetails.builder()
            .id(room.getId())
            .name(room.getRoomName())
            .sorting(room.getSorting())
            .build();
    }

    public static Room mapToRoom(RoomDetails hallDetails) {
        Room room = new Room();
        room.setRoomName(hallDetails.getName());
        room.setSorting(hallDetails.getSorting());

        return room;
    }
}
