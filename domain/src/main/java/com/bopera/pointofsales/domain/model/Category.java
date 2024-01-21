package com.bopera.pointofsales.domain.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

    private Long id;

    private String categoryName;

    private String description;

}
