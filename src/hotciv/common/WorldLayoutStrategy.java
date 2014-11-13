package hotciv.common;

import hotciv.framework.City;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.framework.Unit;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 11/11/14.
 */
public interface WorldLayoutStrategy {
    public void worldLayout(GameImpl gameObj);
}
