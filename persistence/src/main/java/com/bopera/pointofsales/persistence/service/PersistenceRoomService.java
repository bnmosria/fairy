package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.Room;
import com.bopera.pointofsales.persistence.model.HallDetails;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersistenceRoomService {
    private final RoomRepository roomRepository;

    public PersistenceRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<HallDetails> retrieveAllRooms() {
        return this.roomRepository.findAllByOrderBySortingDesc()
            .stream().map(HallDetails::mapFromRoom)
            .collect(Collectors.toList());
    }

    public HallDetails addRoom(HallDetails hallDetails) {
        this.roomRepository.findTopByOrderBySortingDesc()
            .ifPresentOrElse(
                top -> hallDetails.setSorting(top.getSorting() + 1),
                () -> hallDetails.setSorting(0)
            );

        Room room = this.roomRepository.save(HallDetails.mapToRoom(hallDetails));
        hallDetails.setId(room.getId());

        return hallDetails;
    }

    public void removeRoom(long roomId) {
        roomRepository.deleteById(roomId);
    }

    public HallDetails updateRoom(HallDetails hallDetails) {
        return this.roomRepository.findById(hallDetails.getId()).map(
            room -> {
                room.setRoomName(hallDetails.getName());

                roomRepository.save(room);

                return hallDetails;
            }
        ).orElseThrow();
    }

}
