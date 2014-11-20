package hotciv.variants.zetaciv;

import hotciv.framework.Player;
import hotciv.standard.GameImpl;
import hotciv.variants.epsilonciv.EpsilonCivWinnerStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mha2908 on 19/11/14.
 */
public class ZetaCivEpsilonCivWinnerState extends ZetaCivWinnerState {

    private HashMap<Player, Integer> gameWinsAt20;

    public ZetaCivEpsilonCivWinnerState(ZetaCivWinnerStrategy ws, HashMap<Player, Integer> gameWinsAt20) {
        super(ws);
        this.gameWinsAt20 = gameWinsAt20;
    }

    @Override
    public Player getWinner(GameImpl game) {
        EpsilonCivWinnerStrategy ws = new EpsilonCivWinnerStrategy(gameWinsAt20);
        return ws.getWinner(game);
    }
}
