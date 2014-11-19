package hotciv.variants.alphaciv;

import hotciv.common.BattleStrategy;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 18/11/14.
 */
public class AlphaCivBattleStrategy implements BattleStrategy {

    @Override
    public boolean battle(Position attacker, Position defender, GameImpl gameObj) {
        return true;
    }
}
