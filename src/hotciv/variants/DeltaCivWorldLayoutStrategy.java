package hotciv.variants;

import hotciv.common.WorldLayoutStrategy;
import hotciv.standard.GameImpl;
import hotciv.framework.*;
import hotciv.standard.TileImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mha2908 on 13/11/14.
 */
public class DeltaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    private HashMap<Position, String> theWorld;

    public DeltaCivWorldLayoutStrategy(String[] layout) {
        theWorld = new HashMap<Position,String>();
        String line;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout[r];
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = line.charAt(c);
                String type = "error";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }
                Position p = new Position(r, c);
                theWorld.put(p, type);
            }
        }
    }

    @Override
    public void worldLayout(GameImpl gameObj) {
        // make tiles
        for (Map.Entry<Position, String> entry : theWorld.entrySet()) {
            Position p = entry.getKey();
            String t = entry.getValue();
            gameObj.createTile(p, t);
        }
        // and 2 cities
        gameObj.createCity(new Position(8,12), Player.RED);
        gameObj.createCity(new Position(4,5), Player.BLUE);
    }
}
