package de.baernreuther.dart.randomnumberfinish;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RandomNumberFinishState {
    private String userName;
    private int currentNumber;
    @Builder.Default
    private int pointsTotal = 0;
    @Builder.Default
    private List<Integer> checkedNumbers = new ArrayList<>();
    @Builder.Default
    private int missedChecks = 0;

    public void addPoints(int points) {
        this.pointsTotal += points;
    }

    public void incrementMissedTries() {
        this.missedChecks++;
    }
}
