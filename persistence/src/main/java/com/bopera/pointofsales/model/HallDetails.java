package com.bopera.pointofsales.model;

import com.bopera.pointofsales.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class HallDetails {
    private int id;
    private String name;
    private String abbreviation;
    private int sorting;
    private List<HallTableDetails> hallTableDetails;

    public static HallDetails mapFromRoom(Room room) {
        return HallDetails.builder()
            .hallTableDetails(
                room.getRoomTables().stream()
                    .map(HallTableDetails::mapFromRoomTable)
                    .collect(Collectors.toList())
            )
            .id(room.getId())
            .name(room.getRoomname())
            .abbreviation(room.getAbbreviation())
            .sorting(room.getSorting())
            .build();
    }

    public static Room mapToRoom(HallDetails hallDetails) {
        Room room = new Room();
        room.setRoomname(hallDetails.getName());
        room.setSorting(hallDetails.getSorting());
        room.setAbbreviation(hallDetails.getAbbreviation());
        return room;
    }
}
