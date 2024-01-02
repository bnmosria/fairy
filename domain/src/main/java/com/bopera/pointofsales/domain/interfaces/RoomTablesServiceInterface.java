package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.RoomTable;

import java.util.List;

public interface RoomTablesServiceInterface {
    List<RoomTable> getAllByRoomId(Long roomId);

    RoomTable getRoomTableById(Long roomTableId);

    RoomTable saveNewRoomTable(RoomTable roomTable);

    RoomTable updateRoomTable(RoomTable roomTable);

    void deleteRoomTable(Long roomTableId);
}
