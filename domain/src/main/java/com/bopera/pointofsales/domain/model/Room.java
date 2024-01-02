package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.RoomEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Room {
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String name;

    private int sorting;
    private final List<Room> roomTables = new ArrayList<>();

    public static Room mapFromRoomEntity(RoomEntity roomEntity) {
        return Room.builder()
            .id(roomEntity.getId())
            .name(roomEntity.getRoomName())
            .sorting(roomEntity.getSorting())
            .build();
    }

    public static RoomEntity mapToRoomEntity(Room roomDetails) {
        RoomEntity room = new RoomEntity();
        room.setRoomName(roomDetails.getName());
        room.setSorting(roomDetails.getSorting());

        return room;
    }
}
