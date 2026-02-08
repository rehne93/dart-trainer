package de.baernreuther.dart.x01;


import de.baernreuther.dart.x01.model.X01Stat;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class X01Service {
    public static final int DEFAULT = 501;
    private Map<String, X01Stat> games = new HashMap<>();

    public X01Stat initializeOrGet(String username) {
        if (games.containsKey(username)) {
            return games.get(username);
        }
        X01Stat stat = X01Stat.builder().startingScore(DEFAULT).scoreLeft(DEFAULT).build();
        games.putIfAbsent(username, stat);
        return stat;
    }


    public void reset(String username) {
        this.games.remove(username);
    }

    public void score(String username, int score) {
        this.games.get(username).score(score);
    }
}
