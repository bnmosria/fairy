package com.bopera.pointofsales.api.room.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EditRoomTableRequest extends SaveRoomTableRequest {
    private Integer id;
}
