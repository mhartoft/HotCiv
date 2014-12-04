package hotciv.standard;

import hotciv.common.WorldLayoutStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.deltaciv.DeltaCivWorldLayoutStrategy;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mha2908 on 04/12/14.
 */
public class FractalMapAdapter implements WorldLayoutStrategy {
    private ThirdPartyFractalGenerator thirdPartyFractalGenerator;
    private ArrayList<String> rowArray;
    private String[] layout;

    public FractalMapAdapter(){
        thirdPartyFractalGenerator = new ThirdPartyFractalGenerator();
        rowArray = new ArrayList<String>();
        StringBuilder currentRow;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            currentRow = new StringBuilder();
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = thirdPartyFractalGenerator.getLandscapeAt(r,c);
                currentRow.append(tileChar);
            }
            System.out.println(currentRow.toString());
            rowArray.add(currentRow.toString());
        }
        layout = rowArray.toArray(new String[rowArray.size()]);
    }
    @Override
    public void worldLayout(GameImpl gameObj) {
        DeltaCivWorldLayoutStrategy dCWLS = new DeltaCivWorldLayoutStrategy(layout);
        dCWLS.worldLayout(gameObj);
    }
}
