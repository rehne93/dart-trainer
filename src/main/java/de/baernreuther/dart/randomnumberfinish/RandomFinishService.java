package de.baernreuther.dart.randomnumberfinish;

import de.baernreuther.dart.randomnumberfinish.database.RandomNumberFinishStateService;
import de.baernreuther.dart.randomnumberfinish.database.entity.FinishAction;
import de.baernreuther.dart.randomnumberfinish.database.entity.FinishActionType;
import de.baernreuther.dart.randomnumberfinish.database.entity.RandomNumberFinishState;
import de.baernreuther.dart.randomnumberfinish.model.RandomNumberDto;
import de.baernreuther.dart.utility.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomFinishService {

    private final RandomNumberFinishStateService finishStateService;
    private final RandomNumberFinishGameConfiguration randomNumberFinishGameConfiguration;

    public RandomNumberFinishState refreshCurrentState(String userName) {
        int randomNumber = RandomNumberGenerator.getRandomNumber(this.randomNumberFinishGameConfiguration.getMin(), this.randomNumberFinishGameConfiguration.getMax());
        RandomNumberFinishState state = this.finishStateService.getOrCreate(userName);
        state.setCurrentNumber(randomNumber);
        return this.finishStateService.save(state);
    }

    public void check(RandomNumberDto randomNumberDto, String userName) {
        var state = finishStateService.getOrCreate(userName);
        state.addAndExecute(new FinishAction(FinishActionType.CHECK, this.randomNumberFinishGameConfiguration.getMin(), this.randomNumberFinishGameConfiguration.getMax()));
        this.finishStateService.save(state);
    }

    public void miss(String userName) {
        var state = finishStateService.getOrCreate(userName);
        state.addAndExecute(new FinishAction(FinishActionType.MISS, this.randomNumberFinishGameConfiguration.getMin(), this.randomNumberFinishGameConfiguration.getMax()));
        this.finishStateService.save(state);
    }

    public void undo(String userName) {
        var state = this.finishStateService.getOrCreate(userName);
        state.removeAndUndo();
        this.finishStateService.save(state);
    }

    public void reset(String username) {
        this.finishStateService.remove(username);
    }

    public RandomNumberFinishState getCurrentState(String userName) {
        return this.finishStateService.getOrCreate(userName);
    }
}
