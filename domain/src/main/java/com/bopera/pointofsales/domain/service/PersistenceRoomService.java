package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.interfaces.RoomServiceInterface;
import com.bopera.pointofsales.domain.model.RoomDetails;
import com.bopera.pointofsales.persistence.entity.Room;
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
    public List<RoomDetails> retrieveAllRooms() {
        return roomRepository.findAllByOrderBySortingDesc()
            .stream().map(RoomDetails::mapFromRoom)
            .collect(Collectors.toList());
    }

    @Override
    public RoomDetails addRoom(RoomDetails roomDetails) {
        roomRepository.findTopByOrderBySortingDesc()
            .ifPresentOrElse(
                top -> roomDetails.setSorting(top.getSorting() + 1),
                () -> roomDetails.setSorting(0)
            );

        Room room = roomRepository.save(RoomDetails.mapToRoom(roomDetails));
        roomDetails.setId(room.getId());

        return roomDetails;
    }

    @Override
    public void removeRoom(long roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public RoomDetails updateRoom(RoomDetails roomDetails) {
        return roomRepository.findById(roomDetails.getId()).map(
            room -> {
                room.setRoomName(roomDetails.getName());

                roomRepository.save(room);

                return roomDetails;
            }
        ).orElseThrow();
    }

}
