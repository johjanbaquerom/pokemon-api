package com.prueba.accedo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    private String name;
    private String ImageUrl;
    private String details;

}
