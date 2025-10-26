package de.baernreuther.dart.randomnumberfinish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RandomFinishService {

    private final Map<String, RandomNumberFinishState> checkMap = new ConcurrentHashMap<>();

    public RandomNumberFinishState refreshCurrentState(int min, int max, String userName) {
        Random random = new Random();
        int randomNumber = random.nextInt(min, max + 1);
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
            state.getCheckedNumbers().add(state.getCurrentNumber());
            state.addPoints(1); // TODO: Berechnen
        } else {
            state.incrementMissedTries();
        }
    }

    public RandomNumberFinishState getCurrentState(String userName) {
        if (!this.checkMap.containsKey(userName)) {
            return this.refreshCurrentState(2, 100, userName);
        }
        return this.checkMap.get(userName);
    }

}
