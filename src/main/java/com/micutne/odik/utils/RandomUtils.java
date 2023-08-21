package com.micutne.odik.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtils {

    public static String Random6Hex() {
        Random random = new Random();
        StringBuilder hexBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(16); // 0부터 15까지의 난수 생성
            char hexDigit = (randomNumber < 10) ? (char) ('0' + randomNumber) : (char) ('A' + (randomNumber - 10));
            hexBuilder.append(hexDigit);
        }

        return hexBuilder.toString();
    }
}
