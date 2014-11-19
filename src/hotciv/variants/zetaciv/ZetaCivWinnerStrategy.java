package hotciv.variants.zetaciv;

import hotciv.common.WinnerStrategy;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 19/11/14.
 */
public class ZetaCivWinnerStrategy implements WinnerStrategy {
    public ZetaCivWinnerState
        stateOne = new ZetaCivBetaCivWinnerState(this),
        stateTwo = new ZetaCivEpsilonCivWinnerState(this),
        state = stateOne;
    public int nrOfRounds = 0;

    @Override
    public Player getWinner(GameImpl game) {
        nrOfRounds += 1;
        if (nrOfRounds >= 20 && state == stateOne) {
            state = stateTwo;
            game.resetBattleCounts();
        }
        return state.getWinner(game);
    }
}
