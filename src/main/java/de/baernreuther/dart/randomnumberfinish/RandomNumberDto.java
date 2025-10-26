package de.baernreuther.dart.randomnumberfinish;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomNumberDto {
    private int currentNumber;
    private boolean check;

}
