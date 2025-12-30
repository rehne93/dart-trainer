package de.baernreuther.dart.randomnumberfinish;

import de.baernreuther.dart.randomnumberfinish.actions.CheckAction;
import de.baernreuther.dart.randomnumberfinish.actions.MissAction;
import de.baernreuther.dart.randomnumberfinish.database.RandomNumberFinishState;
import de.baernreuther.dart.randomnumberfinish.database.RandomNumberFinishStateService;
import de.baernreuther.dart.randomnumberfinish.model.RandomNumberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomFinishService {

    private final RandomNumberFinishStateService finishStateService;
    private final DifficultyCalculator difficultyCalculator;
    private final GameConfiguration gameConfiguration;

    public RandomNumberFinishState refreshCurrentState(String userName) {
        Random random = new Random();
        int randomNumber = random.nextInt(this.gameConfiguration.getMin(), this.gameConfiguration.getMax() + 1);
        RandomNumberFinishState state = this.finishStateService.getOrCreate(userName);
        state.setCurrentNumber(randomNumber);
        return this.finishStateService.save(state);
    }

    public void check(RandomNumberDto randomNumberDto, String userName) {
        var state = finishStateService.getOrCreate(userName);
        if (randomNumberDto.isCheck()) {
            state.executeAction(new CheckAction(this.difficultyCalculator));
        } else {
            state.executeAction(new MissAction());
        }
        this.finishStateService.save(state);
    }

    public RandomNumberFinishState getCurrentState(String userName) {
        return this.finishStateService.getOrCreate(userName);
    }

    public void undo(String userName) {
        var state = this.finishStateService.getOrCreate(userName);
        state.undoAction();
        this.finishStateService.save(state);
    }


    public void reset(String username) {
        this.finishStateService.remove(username);
    }

}
