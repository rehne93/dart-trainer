package de.baernreuther.dart.randomnumberfinish.database.entity;

import de.baernreuther.dart.randomnumberfinish.DifficultyCalculator;
import de.baernreuther.dart.utility.RandomNumberGenerator;
import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FinishAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private FinishActionType finishActionType;
    private int minGameConfig;
    private int maxGameConfig;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private RandomNumberFinishState randomNumberFinishState;

    public FinishAction(FinishActionType finishActionType, int minGameConfig, int maxGameConfig) {
        this.finishActionType = finishActionType;
        this.minGameConfig = minGameConfig;
        this.maxGameConfig = maxGameConfig;
    }

    public void doAction() {
        if (finishActionType == FinishActionType.MISS) {
            this.randomNumberFinishState.incrementMissedTries();
        }
        if (finishActionType == FinishActionType.CHECK) {
            var currentFinish = this.randomNumberFinishState.getCurrentNumber();
            var newFinish = RandomNumberGenerator.getRandomNumber(minGameConfig, maxGameConfig, randomNumberFinishState.getCheckedNumbers());
            this.randomNumberFinishState.addPoints(DifficultyCalculator.getDifficulty(currentFinish));
            this.randomNumberFinishState.addCheckedNumber(currentFinish);
            this.randomNumberFinishState.setCurrentNumber(newFinish);
        }
    }

    public void undoAction() {
        if (finishActionType == FinishActionType.MISS) {
            this.randomNumberFinishState.decrementMissedTries();
        }
        if (finishActionType == FinishActionType.CHECK) {
            if (this.randomNumberFinishState.getCheckedNumbers().isEmpty()) {
                return;
            }
            var lastCheck = this.randomNumberFinishState.getCheckedNumbers().getLast();
            this.randomNumberFinishState.removeLastCheck();
            this.randomNumberFinishState.removePoints(DifficultyCalculator.getDifficulty(lastCheck));
            this.randomNumberFinishState.setCurrentNumber(lastCheck);
        }
    }

}
