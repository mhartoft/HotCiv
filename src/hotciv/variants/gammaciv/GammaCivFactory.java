package hotciv.variants.gammaciv;

import hotciv.common.*;
import hotciv.variants.alphaciv.*;

/**
 * Created by mha2908 on 20/11/14.
 */
public class GammaCivFactory extends AlphaCivFactory {
    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new GammaCivActionStrategy();
    }
}
