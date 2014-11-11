package hotciv.common;

import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 11/11/14.
 */
public interface UnitActionStrategy {
    public void action(GameImpl gameObj, Position p);
}
