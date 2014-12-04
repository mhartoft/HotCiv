package hotciv.standard;

import hotciv.common.WorldLayoutStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mha2908 on 04/12/14.
 */
public class FractalMapAdapter implements WorldLayoutStrategy {
    private ThirdPartyFractalGenerator thirdPartyFractalGenerator;
    private HashMap<Position, String> theWorld;

    public FractalMapAdapter(){
        thirdPartyFractalGenerator = new ThirdPartyFractalGenerator();
        theWorld = new HashMap<Position,String>();
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = thirdPartyFractalGenerator.getLandscapeAt(r,c);
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
        for (Map.Entry<Position, String> entry : theWorld.entrySet()) {
            Position p = entry.getKey();
            String t = entry.getValue();
            gameObj.createTile(p, t);
        }
    }
}
