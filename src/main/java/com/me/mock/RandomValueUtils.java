package com.me.mock;

import java.util.Date;
import java.util.Random;

public class RandomValueUtils {
    public static <T> T getRandomValue(Class<T> tClass) {
        Random random = new Random(System.currentTimeMillis());
        if (tClass.equals(String.class)) {
            String s = "rand" + random.nextInt(100);
            return (T)s;
        } else if (tClass.equals(Integer.class) || tClass.equals(int.class)) {
            Integer s = 1 + random.nextInt(20);
            return (T)s;
        } else if (tClass.equals(Boolean.class)) {
            Boolean b = random.nextBoolean();
            return (T) b;
        } else if (tClass.equals(Date.class)) {
            Date date = new Date();
            return (T)date;
        } else {
            return null;
        }
    }
}
