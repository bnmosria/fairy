package com.bopera.pointofsales.domain.model;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
}
