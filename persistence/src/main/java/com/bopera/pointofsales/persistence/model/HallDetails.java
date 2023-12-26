package com.bopera.pointofsales.persistence.model;

import com.bopera.pointofsales.persistence.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HallDetails {
    private Long id;
    private String name;
    private int sorting;
    private List<HallTableDetails> hallTableDetails;

    public static HallDetails mapFromRoom(Room room) {
        return HallDetails.builder()
            .id(room.getId())
            .name(room.getRoomName())
            .sorting(room.getSorting())
            .build();
    }

    public static Room mapToRoom(HallDetails hallDetails) {
        Room room = new Room();
        room.setRoomName(hallDetails.getName());
        room.setSorting(hallDetails.getSorting());

        return room;
    }
}
