package com.bopera.pointofsales.api.room.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveRoomRequest {
	@NotBlank
	@Size(min = 3)
	private String name;
	private String abbreviation;
	private int sorting;
}
