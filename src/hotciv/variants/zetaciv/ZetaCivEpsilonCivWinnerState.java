package hotciv.variants.zetaciv;

import hotciv.framework.Player;
import hotciv.standard.GameImpl;
import hotciv.variants.epsilonciv.EpsilonCivWinnerStrategy;

/**
 * Created by mha2908 on 19/11/14.
 */
public class ZetaCivEpsilonCivWinnerState extends ZetaCivWinnerState {
    public ZetaCivEpsilonCivWinnerState(ZetaCivWinnerStrategy ws) { super(ws); }
    @Override
    public Player getWinner(GameImpl game) {
        EpsilonCivWinnerStrategy ws = new EpsilonCivWinnerStrategy();
        return ws.getWinner(game);
    }
}
