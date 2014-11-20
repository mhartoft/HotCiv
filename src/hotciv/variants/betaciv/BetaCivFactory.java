package hotciv.variants.betaciv;

import hotciv.common.*;
import hotciv.variants.alphaciv.*;

/**
 * Created by mha2908 on 20/11/14.
 */
public class BetaCivFactory extends AlphaCivFactory {

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new BetaCivWinnerStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new BetaCivAgingStrategy();
    }
}
