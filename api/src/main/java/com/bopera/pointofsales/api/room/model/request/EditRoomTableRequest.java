package com.bopera.pointofsales.api.room.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EditRoomTableRequest extends SaveRoomTableRequest {
    private Integer id;
}
