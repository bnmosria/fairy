package com.bopera.pointofsales.api.room.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EditRoomRequest extends SaveRoomRequest {
	@NotNull
	@Min(1)
	private long id;
}
