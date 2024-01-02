package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.interfaces.RoomServiceInterface;
import com.bopera.pointofsales.domain.model.Room;
import com.bopera.pointofsales.persistence.entity.RoomEntity;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersistenceRoomService implements RoomServiceInterface {
    private final RoomRepository roomRepository;

    public PersistenceRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> retrieveAllRooms() {
        return roomRepository.findAllByOrderBySortingDesc()
            .stream().map(Room::mapFromRoom)
            .collect(Collectors.toList());
    }

    @Override
    public Room addRoom(Room roomDetails) {
        roomRepository.findTopByOrderBySortingDesc()
            .ifPresentOrElse(
                top -> roomDetails.setSorting(top.getSorting() + 1),
                () -> roomDetails.setSorting(0)
            );

        RoomEntity room = roomRepository.save(Room.mapToRoom(roomDetails));
        roomDetails.setId(room.getId());

        return roomDetails;
    }

    @Override
    public void removeRoom(long roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public Room updateRoom(Room roomDetails) {
        return roomRepository.findById(roomDetails.getId()).map(
            room -> {
                room.setRoomName(roomDetails.getName());
                room.setSorting(roomDetails.getSorting());

                roomRepository.save(room);

                return roomDetails;
            }
        ).orElseThrow();
    }

}
