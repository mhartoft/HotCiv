package hotciv.variants.zetaciv;

import hotciv.common.WinnerStrategy;

/**
 * Created by mha2908 on 19/11/14.
 */
public abstract class ZetaCivWinnerState implements WinnerStrategy {
    protected ZetaCivWinnerStrategy winnerStrategy;
    public ZetaCivWinnerState(ZetaCivWinnerStrategy ws){ winnerStrategy = ws; }
}