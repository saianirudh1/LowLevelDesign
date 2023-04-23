package components;

import java.util.Random;

import lombok.AllArgsConstructor;

import static components.Constants.maxDiceValue;
import static components.Constants.minDiceValue;

@AllArgsConstructor
public class Dice {
    private Integer noOfDice;

    public Integer rollDice() {
        int diceRoll = 0;
        Random random = new Random();
        for(int count = 0; count < noOfDice; count++) {
            diceRoll += random.nextInt(maxDiceValue - 1) + minDiceValue;
        }

        return diceRoll;
    }
}
