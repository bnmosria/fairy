package com.bopera.pointofsales.auth.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditHallRequest {
	private int id;
	@NotBlank
	@Size(min = 3)
	private String name;
	private String abbreviation;
	private int sorting;
}
