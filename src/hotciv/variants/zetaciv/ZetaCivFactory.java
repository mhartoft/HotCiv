package hotciv.variants.zetaciv;

import hotciv.variants.alphaciv.AlphaCivFactory;
import hotciv.common.WinnerStrategy;

/**
 * Created by mha2908 on 20/11/14.
 */
public class ZetaCivFactory extends AlphaCivFactory {
    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new ZetaCivWinnerStrategy();
    }
}
