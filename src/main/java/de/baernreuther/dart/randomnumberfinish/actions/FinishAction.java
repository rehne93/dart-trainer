package de.baernreuther.dart.randomnumberfinish.actions;

import de.baernreuther.dart.randomnumberfinish.model.RandomNumberDto;
import de.baernreuther.dart.randomnumberfinish.model.RandomNumberFinishState;

public interface FinishAction {

    RandomNumberFinishState execute(RandomNumberFinishState state);

    RandomNumberFinishState undo(RandomNumberFinishState state);
}
