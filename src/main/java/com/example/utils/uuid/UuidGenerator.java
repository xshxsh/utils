package com.example.utils.uuid;

import java.util.Random;

/**
 * @author: shihai.xie
 * @create: 2021-08-24 16:47
 * @description: UUID生成器
 **/

public class UuidGenerator {

    static final Random RANDOM = new Random();
    static final int[] NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static final char[] LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * 获取指定位数的随机数字字符串
     */
    public static String getNumberUuid(int size) {
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < size; i++) {
            uuid.append(NUMBERS[RANDOM.nextInt(10)]);
        }
        return uuid.toString();
    }

    /**
     * 获取指定位数的随机字母字符串
     */
    public static String getCharUuid(int size) {
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < size; i++) {
            uuid.append(LETTERS[RANDOM.nextInt(26)]);
        }
        return uuid.toString();
    }

}
