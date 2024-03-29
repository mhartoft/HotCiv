package hotciv.variants.gammaciv;

import hotciv.common.UnitActionStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.variants.thetaciv.ThetaCivConstants;

/**
 * Created by mha2908 on 11/11/14.
 */
public class GammaCivActionStrategy implements UnitActionStrategy {

    @Override
    public void action(GameImpl gameObj, Position p) {
        UnitImpl u = (UnitImpl) gameObj.getUnitAt(p);
        String t = u.getTypeString();
        if (t == GameConstants.SETTLER){
            gameObj.removeUnit(p);
            gameObj.createCity(p, u.getOwner());
        }
        if (t == GameConstants.ARCHER || t == ThetaCivConstants.CHARIOT){
            // added chariot for thetaciv. does not affect gammaciv and saves us a lot of repetition
            // is this ok? We feel that this one little addition is harmless
            // and the 3-1-2 model is a bit overkill here.
            u.setFortified();
            if(u.getFortified() == true) {
                u.setDefensiveStrength(u.getDefensiveStrength() * 2);
            } else {
                u.setDefensiveStrength(u.getDefensiveStrength() / 2);
            }
        }
    }
}
