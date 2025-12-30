package de.baernreuther.dart.randomnumberfinish;

public class DifficultyCalculator {

    public static int getDifficulty(int number) {
        if (number <= 40) {
            return 1;
        }
        if (number <= 60) {
            return 2;
        }
        if (number <= 80) {
            return 3;
        }
        if (number <= 100) {
            return 4;
        }
        if (number <= 130) {
            return 5;
        }
        return 6;
    }
}
