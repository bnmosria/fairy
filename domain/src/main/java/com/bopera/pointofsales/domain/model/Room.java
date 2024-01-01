package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.RoomEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room {
    private Long id;
    private String name;
    private int sorting;
    private List<RoomTable> hallTableDetails;

    public static Room mapFromRoom(RoomEntity room) {
        return Room.builder()
            .id(room.getId())
            .name(room.getRoomName())
            .sorting(room.getSorting())
            .build();
    }

    public static RoomEntity mapToRoom(Room hallDetails) {
        RoomEntity room = new RoomEntity();
        room.setRoomName(hallDetails.getName());
        room.setSorting(hallDetails.getSorting());

        return room;
    }
}
