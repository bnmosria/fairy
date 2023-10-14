package com.bopera.pointofsales.model;

import com.bopera.pointofsales.entity.Room;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HallTableDetails {
    private Integer id;
    private String title;
    private String code;
    private String name;
    private Integer roomId;
    private Integer active;
    private Integer sorting;
}
