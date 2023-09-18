package com.bopera.pointofsales.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoomDetails {
    private int id;
    private String name;
    private String abbreviation;
    private int sorting;
    private Set<RoomTableDetails> roomTableDetails;
}
