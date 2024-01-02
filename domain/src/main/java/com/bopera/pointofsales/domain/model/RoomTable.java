package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.RoomTableEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomTable {
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String name;

    @Min(1)
    @NotNull
    private Long roomId;

    private String tableNumber;
    private int active;
    private int sorting;

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
        roomTableEntity.setSorting(roomTable.getSorting());

        return roomTableEntity;
    }
}
