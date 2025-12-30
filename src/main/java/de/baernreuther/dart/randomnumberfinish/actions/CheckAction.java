package de.baernreuther.dart.randomnumberfinish.actions;

import de.baernreuther.dart.randomnumberfinish.DifficultyCalculator;
import de.baernreuther.dart.randomnumberfinish.database.RandomNumberFinishState;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckAction implements FinishAction {

    private final DifficultyCalculator difficultyCalculator;


    public RandomNumberFinishState execute(RandomNumberFinishState state) {
        state.getCheckedNumbers().add(state.getCurrentNumber());
        state.addPoints(this.difficultyCalculator.getDifficulty(state.getCurrentNumber()));
        return state;
    }

    @Override
    public RandomNumberFinishState undo(RandomNumberFinishState state) {
        var lastCheck = state.getCheckedNumbers().getLast();
        state.getCheckedNumbers().removeLast();
        state.removePoints(this.difficultyCalculator.getDifficulty(lastCheck));
        state.setCurrentNumber(lastCheck);
        return state;
    }
}
