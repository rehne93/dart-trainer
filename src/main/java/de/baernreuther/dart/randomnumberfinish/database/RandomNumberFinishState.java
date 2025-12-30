package de.baernreuther.dart.randomnumberfinish.database;

import de.baernreuther.dart.randomnumberfinish.actions.CheckAction;
import de.baernreuther.dart.randomnumberfinish.actions.FinishAction;
import de.baernreuther.dart.security.database.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RandomNumberFinishState {

    @Id
    @Generated
    private int id;
    @OneToOne
    private User appUser;
    @Builder.Default
    private int currentNumber = 2;
    @Builder.Default
    private int pointsTotal = 0;
    @Builder.Default
    private List<Integer> checkedNumbers = new ArrayList<>();
    @Builder.Default
    private int missedChecks = 0;

    @Transient
    @Builder.Default
    private List<FinishAction> finishActions = new ArrayList<>();


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
        if (missedChecks == 0 && checkedNumbers.isEmpty()) {
            return 0;
        }
        return (float) checkedNumbers.size() / (missedChecks + checkedNumbers.size());
    }

    public RandomNumberFinishState executeAction(FinishAction action) {
        this.finishActions.add(action);
        return action.execute(this);
    }

    public RandomNumberFinishState undoAction() {
        var lastAction = this.finishActions.getLast();
        return lastAction.undo(this);
    }
}
