package hotciv.standard;

import hotciv.common.DiceStrategy;

/**
 * Created by mha2908 on 18/11/14.
 */
public class FixedDiceStrategy implements DiceStrategy {

    @Override
    public int getValue() {
        return 1; // renders the dice insignificant and allow us to predict battles
    }
}
