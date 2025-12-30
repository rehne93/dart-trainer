package de.baernreuther.dart.randomnumberfinish.actions;

import de.baernreuther.dart.randomnumberfinish.database.RandomNumberFinishState;

public interface FinishAction {

    RandomNumberFinishState execute(RandomNumberFinishState state);

    RandomNumberFinishState undo(RandomNumberFinishState state);
}
