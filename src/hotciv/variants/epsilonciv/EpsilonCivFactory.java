package hotciv.variants.epsilonciv;

import hotciv.common.*;
import hotciv.framework.Player;
import hotciv.variants.alphaciv.*;

import java.util.HashMap;

/**
 * Created by mha2908 on 20/11/14.
 */
public class EpsilonCivFactory extends AlphaCivFactory {
    private DiceStrategy ds;

    public EpsilonCivFactory(DiceStrategy ds) {
        this.ds = ds;
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new EpsilonCivWinnerStrategy(new HashMap<Player, Integer>());
    }

    @Override
    public BattleStrategy createBattleStrategy() {
        return new EpsilonCivBattleStrategy(ds);
    }
}
