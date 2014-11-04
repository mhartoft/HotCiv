package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

/**
 * Created by mha2908 on 04/11/14.
 */
public class TileImpl implements Tile {
    private String type;

    public TileImpl(String type){
        this.type = type;
    }

    @Override
    public String getTypeString() {
        return type;
    }
}
