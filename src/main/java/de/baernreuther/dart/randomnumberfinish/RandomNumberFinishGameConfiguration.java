package de.baernreuther.dart.randomnumberfinish;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class RandomNumberFinishGameConfiguration {

    private int min = 2;
    private int max = 100;

}
