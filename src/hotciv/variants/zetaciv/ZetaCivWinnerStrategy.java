package hotciv.variants.zetaciv;

import hotciv.common.WinnerStrategy;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;
import hotciv.variants.betaciv.BetaCivWinnerStrategy;
import hotciv.variants.epsilonciv.EpsilonCivWinnerStrategy;

import java.util.HashMap;

/**
 * Created by mha2908 on 19/11/14.
 */
public class ZetaCivWinnerStrategy implements WinnerStrategy {
    public WinnerStrategy
        stateOne = new BetaCivWinnerStrategy(), // we do not yet make stateTwo
        state = stateOne;
    public int nrOfRounds = 0;
    public HashMap<Player, Integer> battleWinsAt20;

    @Override
    public Player getWinner(GameImpl game) {
        if (game.getBecauseEndOfRound()) nrOfRounds += 1;
        if (nrOfRounds == 20 && state == stateOne) {
            battleWinsAt20 = (HashMap<Player,Integer>) game.getBattleWins().clone();
            WinnerStrategy stateTwo = new EpsilonCivWinnerStrategy(battleWinsAt20);
            state = stateTwo;
        }
        return state.getWinner(game);
    }
}