package com.example.coffee_v2.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apple {

    private String name;
    private String price;
    private String color;
    private String size;
    private String description;
    private String origin;
    private int weight;

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }
}
