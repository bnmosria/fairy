package com.bopera.pointofsales.api.room.model.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveRoomTableRequest {
	private Integer id;

	@NotBlank
	@Size(min = 3)
	private String title;
	private String code;
	private String name;

	@Digits(integer = 10, fraction = 0)
	private Long roomId;
	private Integer active;
	private Integer sorting;
}
