package com.codecool.dungeoncrawl;

import java.util.Random;

public class Util {
    public static int getRandomInt() {
        Random random = new Random();
        int min = -1;
        int max = 2;
        int number;
        number = random.nextInt(max - min) + min;
        return number;
    }

}
