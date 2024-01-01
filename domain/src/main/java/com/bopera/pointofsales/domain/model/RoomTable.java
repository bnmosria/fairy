package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.RoomTableEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomTable {
    private Long id;
    private String title;
    private String code;
    private String name;
    private Long roomId;
    private Integer active;
    private Integer sorting;

    public static RoomTable mapFromRoomTable(RoomTableEntity roomTable) {
        return RoomTable.builder()
            .id(roomTable.getId())
            .build();
    }
}
