package com.micutne.odik.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtils {

    public static String Random6Number() {
        Random random = new Random();
        StringBuilder numberBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(9); // 0부터 15까지의 난수 생성
            numberBuilder.append(randomNumber);
        }

        return numberBuilder.toString();
    }
}
