package hotciv.common;

import hotciv.framework.Position;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 26/11/14.
 */
public interface UnitProductionStrategy {
    public void changeProductionInCityAt(GameImpl game, Position p, String unitType);
}
