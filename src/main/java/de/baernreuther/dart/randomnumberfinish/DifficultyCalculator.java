package de.baernreuther.dart.randomnumberfinish;

import org.springframework.stereotype.Service;

@Service
public class DifficultyCalculator {

    public int getDifficulty(int number) {
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
        return 5;
    }
}
