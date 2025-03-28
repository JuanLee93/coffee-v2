package com.example.coffee_v2.test;

import com.example.coffee_v2.test.dto.Apple;

public class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals("green");
    }
}
