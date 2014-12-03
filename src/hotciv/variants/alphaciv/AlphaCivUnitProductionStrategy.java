package hotciv.variants.alphaciv;

import hotciv.common.UnitProductionStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 26/11/14.
 */
public class AlphaCivUnitProductionStrategy implements UnitProductionStrategy {
    @Override
    public void changeProductionInCityAt(GameImpl game, Position p, String unitType) {
        CityImpl c = game.getCityAt(p);
        if (unitType.equals(GameConstants.ARCHER)){
            c.setProduction(GameConstants.ARCHER);
        }else if (unitType.equals(GameConstants.LEGION)){
            c.setProduction(GameConstants.LEGION);
        }else if (unitType.equals(GameConstants.SETTLER)){
            c.setProduction(GameConstants.SETTLER);
        }
    }
}
