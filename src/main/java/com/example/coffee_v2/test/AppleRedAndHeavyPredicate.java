package com.example.coffee_v2.test;

import com.example.coffee_v2.test.dto.Apple;

public class AppleRedAndHeavyPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals("red") && apple.getWeight() > 150;
    }
}
