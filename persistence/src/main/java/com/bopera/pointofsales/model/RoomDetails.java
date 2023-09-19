package com.bopera.pointofsales.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomDetails {
    private int id;
    private String name;
    private String abbreviation;
    private int sorting;
    private List<RoomTableDetails> roomTableDetails;
}
