package cn.maoookai.util;

import java.util.Random;

public class RandomNumberUtil {
    public static int getRandomNumber(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber);
    }
}
