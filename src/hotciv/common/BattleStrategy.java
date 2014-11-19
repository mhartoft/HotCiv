package hotciv.common;

import hotciv.framework.Position;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 18/11/14.
 */
public interface BattleStrategy {
    public boolean battle(Position attacker, Position defender, GameImpl gameObj);
}
