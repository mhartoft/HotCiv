package hotciv.variants;

import hotciv.common.UnitActionStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

/**
 * Created by mha2908 on 11/11/14.
 */
public class GammaCivAction implements UnitActionStrategy {

    @Override
    public void action(GameImpl gameObj, Position p) {
        UnitImpl u = (UnitImpl) gameObj.getUnitAt(p);
        String t = u.getTypeString();
        if (t == GameConstants.SETTLER){
            gameObj.removeUnit(p);
            gameObj.createCity(p, u.getOwner());
        }
        if (t == GameConstants.ARCHER){
            u.setFortified();
            if(u.getFortified() == true) {
                u.setDefensiveStrength(u.getDefensiveStrength() * 2);
            } else {
                u.setDefensiveStrength(u.getDefensiveStrength() / 2);
            }
        }
    }
}
