package com.example.coffee_v2.test;

import com.example.coffee_v2.test.dto.Apple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class TestService {

    public static List<Apple> filterApples(List<Apple> apples, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void prettyPrintApple(List<Apple> apples, AppleFormatter formatter) {
        for (Apple apple : apples) {
            String formattedApple = formatter.format(apple);
            log.info(formattedApple);
        }
    }

    List<Apple> apples = Arrays.asList(new Apple(80, "green"),
                                       new Apple(155, "green"),
                                       new Apple(120, "red"));

    public void appleFilters() {
        List<Apple> apples1 = filterApples(apples, new AppleRedAndHeavyPredicate());
        List<Apple> apples2 = filterApples(apples, new AppleGreenColorPredicate());
    }

    public void temp() {
        Runnable r1 = () -> System.out.println("hello world");

        // 자바 익명클래스
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("hello world");
            }
        };


    }
}
