package com.bopera.pointofsales.model;

import com.bopera.pointofsales.entity.RoomTable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HallTableDetails {
    private Integer id;
    private String title;
    private String code;
    private String name;
    private Integer roomId;
    private Integer active;
    private Integer sorting;

    public static HallTableDetails mapFromRoomTable(RoomTable roomTable) {
        return HallTableDetails.builder()
            .roomId(roomTable.getId())
            .sorting(roomTable.getSorting())
            .title(roomTable.getTableno())
            .code(roomTable.getCode())
            .name(roomTable.getName())
            .id(roomTable.getId())
            .active(roomTable.getActive())
            .build();
    }
}
