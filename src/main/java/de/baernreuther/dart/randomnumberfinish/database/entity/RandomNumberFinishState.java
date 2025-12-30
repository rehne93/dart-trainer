package de.baernreuther.dart.randomnumberfinish.database.entity;

import de.baernreuther.dart.security.database.entity.User;
import jakarta.persistence.*;
import lombok.*;

import javax.swing.*;
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

    @Builder.Default
    @OneToMany(mappedBy = "randomNumberFinishState", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinishAction> actions = new ArrayList<>();


    public void addPoints(int points) {
        this.pointsTotal += points;
    }

    public void removePoints(int points) {
        this.pointsTotal -= points;
    }

    public void removeLastCheck() {
        this.checkedNumbers.remove(this.checkedNumbers.size() - 1);
    }

    public void incrementMissedTries() {
        this.missedChecks++;
    }

    public void decrementMissedTries() {
        this.missedChecks--;
    }

    public void addCheckedNumber(int number) {
        this.checkedNumbers.add(number);
    }

    public float getQuote() {
        if (missedChecks == 0 && checkedNumbers.isEmpty()) {
            return 0;
        }
        return (float) checkedNumbers.size() / (missedChecks + checkedNumbers.size());
    }

    public void addAndExecute(FinishAction action) {
        action.setRandomNumberFinishState(this);
        actions.add(action);
        action.doAction();
    }

    public void removeAndUndo() {
        var lastAction = actions.getLast();
        lastAction.undoAction();
        this.actions.remove(lastAction);
    }

}
