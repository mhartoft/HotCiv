package hotciv.variants.zetaciv;

import hotciv.framework.Player;
import hotciv.standard.GameImpl;
import hotciv.variants.betaciv.BetaCivWinnerStrategy;

/**
 * Created by mha2908 on 19/11/14.
 */
public class ZetaCivBetaCivWinnerState extends ZetaCivWinnerState {
    public ZetaCivBetaCivWinnerState(ZetaCivWinnerStrategy ws){ super(ws); }

    @Override
    public Player getWinner(GameImpl game) {
        BetaCivWinnerStrategy ws = new BetaCivWinnerStrategy();
        return ws.getWinner(game);
    }
}
