package com.tj_JavaEE.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private int id;
    private String description;
    private String imageUrl;
    private String name;

}
