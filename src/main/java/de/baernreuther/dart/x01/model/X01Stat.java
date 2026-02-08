package de.baernreuther.dart.x01.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class X01Stat {
    private int startingScore;
    private int scoreLeft;
    @Builder.Default
    private List<Integer> scores = new ArrayList<>();

    public void score(int score) {
        if (score < 0 || score > 180) {
            return;
        }
        scores.add(score);
        if (scoreLeft - score < 0) {
            return;
        }
        scoreLeft -= score;
    }

    public int getRound() {
        return scores.size();
    }
}
