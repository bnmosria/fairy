package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.RoomTableEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomTable {
    private Long id;
    private String tableNumber;
    private String name;
    private Long roomId;
    private Integer active;
    private Integer sorting;

    public static RoomTable mapFromRoomTableEntity(RoomTableEntity roomTableEntity) {
        return RoomTable.builder()
            .id(roomTableEntity.getId())
            .name(roomTableEntity.getName())
            .tableNumber(roomTableEntity.getTableNumber())
            .active(roomTableEntity.getActive())
            .sorting(roomTableEntity.getSorting())
            .build();
    }

    public static RoomTableEntity mapToRoomTableEntity(RoomTable roomTable) {
        RoomTableEntity roomTableEntity = new RoomTableEntity();
        roomTableEntity.setName(roomTable.getName());
        roomTableEntity.setActive(roomTable.getActive());
        roomTableEntity.setTableNumber(roomTable.getTableNumber());

        return roomTableEntity;
    }
}
