package de.baernreuther.dart.randomnumberfinish;

import de.baernreuther.dart.randomnumberfinish.actions.CheckAction;
import de.baernreuther.dart.randomnumberfinish.actions.MissAction;
import de.baernreuther.dart.randomnumberfinish.model.RandomNumberDto;
import de.baernreuther.dart.randomnumberfinish.model.RandomNumberFinishState;
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

    private final Map<String, RandomNumberFinishState> checkMap = new ConcurrentHashMap<>();
    private final DifficultyCalculator difficultyCalculator;
    private final GameConfiguration gameConfiguration;

    public RandomNumberFinishState refreshCurrentState(String userName) {
        Random random = new Random();
        int randomNumber = random.nextInt(this.gameConfiguration.getMin(), this.gameConfiguration.getMax() + 1);
        RandomNumberFinishState state = null;
        if (checkMap.containsKey(userName)) {
            state = checkMap.get(userName);
            state.setCurrentNumber(randomNumber);
            checkMap.put(userName, state);
        } else {
            state = RandomNumberFinishState.builder()
                    .currentNumber(randomNumber)
                    .userName(userName)
                    .build();
            checkMap.put(userName, state);
        }
        return state;
    }

    public void check(RandomNumberDto randomNumberDto, String userName) {
        var state = this.checkMap.get(userName);
        if (randomNumberDto.isCheck()) {
            state.executeAction(new CheckAction(this.difficultyCalculator));
        } else {
            state.executeAction(new MissAction());
        }
    }

    public RandomNumberFinishState getCurrentState(String userName) {
        if (!this.checkMap.containsKey(userName)) {
            return this.refreshCurrentState(userName);
        }
        return this.checkMap.get(userName);
    }

    public void undo(String userName) {
        if(!this.checkMap.containsKey(userName)) {
            log.warn("Undo without State in map by {}", userName);
            return;
        }

        this.checkMap.put(userName, this.checkMap.get(userName).undoAction());
    }


    public void reset(String username) {
        this.checkMap.remove(username);
    }

}
