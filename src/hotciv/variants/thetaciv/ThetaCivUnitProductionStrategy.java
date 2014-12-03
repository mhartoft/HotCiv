package hotciv.variants.thetaciv;

import hotciv.common.UnitProductionStrategy;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.variants.alphaciv.AlphaCivUnitProductionStrategy;

/**
 * Created by mha2908 on 26/11/14.
 */
public class ThetaCivUnitProductionStrategy implements UnitProductionStrategy {
    private AlphaCivUnitProductionStrategy as;

    public ThetaCivUnitProductionStrategy() {
        this.as = new AlphaCivUnitProductionStrategy();
    }


    @Override
    public void changeProductionInCityAt(GameImpl game, Position p, String unitType) {
        CityImpl c = game.getCityAt(p);
        if (unitType.equals(ThetaCivConstants.CHARIOT)){
            c.setProduction(ThetaCivConstants.CHARIOT);
        }else{
            as.changeProductionInCityAt(game, p, unitType);
        }
    }
}
