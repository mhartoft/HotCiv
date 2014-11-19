package hotciv.standard;

import hotciv.common.DiceStrategy;

import java.util.Random;

/**
 * Created by mha2908 on 18/11/14.
 */
public class RealDiceStrategy implements DiceStrategy {
    @Override
    public int getValue() {
        Random rand = new Random();
        return rand.nextInt(6)+1;
        // rand.nextInt(n) returns [0;n[
        // hence rand.nextInt(6)+1 to get [1;6]
    }
}
