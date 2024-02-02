package com.bopera.pointofsales.api.room.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveRoomRequest {
	@NotBlank
	@Size(min = 3)
	private String name;
	private String abbreviation;
	private int sorting;
}
