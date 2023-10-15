package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.model.HallDetails;
import com.bopera.pointofsales.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomsService {
    private final RoomRepository roomRepository;

    public RoomsService(RoomRepository roomRepository) {
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

    public void removeRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

    public HallDetails editRoom(HallDetails hallDetails) {
        return this.roomRepository.findById(hallDetails.getId()).map(
            room -> {
                room.setRoomname(hallDetails.getName());
                room.setAbbreviation(hallDetails.getAbbreviation());

                roomRepository.save(room);

                return hallDetails;
            }
        ).orElseThrow();
    }

}
