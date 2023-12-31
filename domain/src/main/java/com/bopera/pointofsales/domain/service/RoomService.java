package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.HallDetails;
import com.bopera.pointofsales.persistence.entity.Room;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<HallDetails> retrieveAllRooms() {
        return roomRepository.findAllByOrderBySortingDesc()
            .stream().map(HallDetails::mapFromRoom)
            .collect(Collectors.toList());
    }

    public HallDetails addRoom(HallDetails hallDetails) {
        roomRepository.findTopByOrderBySortingDesc()
            .ifPresentOrElse(
                top -> hallDetails.setSorting(top.getSorting() + 1),
                () -> hallDetails.setSorting(0)
            );

        Room room = roomRepository.save(HallDetails.mapToRoom(hallDetails));
        hallDetails.setId(room.getId());

        return hallDetails;
    }

    public void removeRoom(long roomId) {
        roomRepository.deleteById(roomId);
    }

    public HallDetails updateRoom(HallDetails hallDetails) {
        return roomRepository.findById(hallDetails.getId()).map(
            room -> {
                room.setRoomName(hallDetails.getName());

                roomRepository.save(room);

                return hallDetails;
            }
        ).orElseThrow();
    }

}
