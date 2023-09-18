package com.bopera.pointofsales.model;

import com.bopera.pointofsales.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RoomTableDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String name;
    private Room room;
    private Integer active;
    private Integer sorting;
}
