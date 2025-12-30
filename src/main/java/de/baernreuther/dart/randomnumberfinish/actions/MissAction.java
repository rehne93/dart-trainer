package de.baernreuther.dart.randomnumberfinish.actions;

import de.baernreuther.dart.randomnumberfinish.database.RandomNumberFinishState;

public class MissAction implements FinishAction{

    public RandomNumberFinishState execute( RandomNumberFinishState updatedState) {
        updatedState.incrementMissedTries();
        return updatedState;
    }

    public RandomNumberFinishState undo(RandomNumberFinishState state) {
        state.decrementMissedTries();
        return state;
    }
}
