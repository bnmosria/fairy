package com.bopera.pointofsales.persistence.model;

import com.bopera.pointofsales.persistence.entity.RoomTable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HallTableDetails {
    private Long id;
    private String title;
    private String code;
    private String name;
    private Long roomId;
    private Integer active;
    private Integer sorting;

    public static HallTableDetails mapFromRoomTable(RoomTable roomTable) {
        return HallTableDetails.builder()
            .id(roomTable.getId())
            .build();
    }
}
