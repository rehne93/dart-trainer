package de.baernreuther.dart.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumberGenerator {

    private final static List<Integer> FORBIDDEN = List.of(159, 162, 163, 165, 166, 168, 169);
    private final static Random RANDOM = new Random();

    public static int getRandomNumber(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static int getRandomNumber(int min, int max, List<Integer> ignoreList) {
        List<Integer> lastTenEntries = new ArrayList<>(
                ignoreList.subList(Math.max(ignoreList.size() - 10, 0), ignoreList.size())
        );
        int result = getRandomNumber(min, max);
        if (lastTenEntries.contains(result) || FORBIDDEN.contains(result)) {
            return getRandomNumber(min, max, ignoreList);
        }
        return result;
    }
}
