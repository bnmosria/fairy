package com.bopera.pointofsales.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Permission {
  private String name;

  private String description;
}
