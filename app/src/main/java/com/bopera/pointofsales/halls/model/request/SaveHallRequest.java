package com.bopera.pointofsales.halls.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveHallRequest {
	@NotBlank
	@Size(min = 3)
	private String name;
	private String abbreviation;
	private int sorting;
}
