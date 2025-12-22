package de.baernreuther.dart.randomnumberfinish.model;

import de.baernreuther.dart.randomnumberfinish.actions.FinishAction;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RandomNumberFinishState {
    private String userName;
    @Builder.Default
    private int currentNumber = 2;
    @Builder.Default
    private int pointsTotal = 0;
    @Builder.Default
    private List<Integer> checkedNumbers = new ArrayList<>();
    @Builder.Default
    private int missedChecks = 0;

    @Builder.Default
    private List<FinishAction> actions = new ArrayList<>();

    public void addPoints(int points) {
        this.pointsTotal += points;
    }

    public void removePoints(int points) {
        this.pointsTotal -= points;
    }

    public void incrementMissedTries() {
        this.missedChecks++;
    }
    public void decrementMissedTries() {
        this.missedChecks--;
    }

    public float getQuote() {
        if(missedChecks == 0 && checkedNumbers.isEmpty()) {
            return 0;
        }
        return (float) checkedNumbers.size() / (missedChecks+checkedNumbers.size());
    }

    public RandomNumberFinishState executeAction(FinishAction action) {
        this.actions.add(action);
        return action.execute(this);
    }

    public RandomNumberFinishState undoAction() {
        var lastAction = this.actions.getLast();
        return lastAction.undo(this);
    }
}
