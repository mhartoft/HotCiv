package hotciv.variants.thetaciv;

import hotciv.common.UnitActionStrategy;
import hotciv.common.UnitProductionStrategy;
import hotciv.variants.alphaciv.AlphaCivFactory;
import hotciv.variants.gammaciv.GammaCivActionStrategy;
import hotciv.variants.gammaciv.GammaCivFactory;

/**
 * Created by mha2908 on 20/11/14.
 */
public class ThetaCivFactory extends GammaCivFactory {
    @Override
    public UnitProductionStrategy createUnitProductionStrategy() {
        return new ThetaCivUnitProductionStrategy();
    }
}