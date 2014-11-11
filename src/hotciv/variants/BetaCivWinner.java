package hotciv.variants;

import hotciv.common.WinnerStrategy;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mha2908 on 11/11/14.
 */
public class BetaCivWinner implements WinnerStrategy {

    @Override
    public Player getWinner(GameImpl gameObj) {
        HashMap<Position, CityImpl> cities = gameObj.getCities();
        boolean sameOwner = true;
        Player lastOwner = null;

        for (Map.Entry<Position, CityImpl> entry : cities.entrySet()) {
            Position p = entry.getKey();
            CityImpl c = entry.getValue();
            // If no cities has been visited (first iteration)
            // then set lastOwner to current city's owner
            // and skip the rest of the for loop
            if (lastOwner == null) {
                lastOwner = c.getOwner();
                continue;
            }
            // If the current city's owner is not the same as the lastOwner
            // (at any given time), the sameOwner is set to false, and we are done
            if (lastOwner != c.getOwner()){
                sameOwner = false;
                break;
            }
        }
        if (sameOwner) return lastOwner;
        return null;
    }
}
