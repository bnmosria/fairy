package com.bopera.pointofsales.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDetails {
    private int id;
    private String name;
    private String abbreviation;
    private int sorting;
}
