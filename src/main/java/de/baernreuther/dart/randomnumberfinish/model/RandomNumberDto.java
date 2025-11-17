package de.baernreuther.dart.randomnumberfinish.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomNumberDto {
    private int currentNumber;
    private boolean check;

}
