package hotciv.variants.epsilonciv;

import hotciv.common.WinnerStrategy;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mha2908 on 19/11/14.
 */
public class EpsilonCivWinnerStrategy implements WinnerStrategy{

    private HashMap<Player, Integer> battleWinsInit;

    public EpsilonCivWinnerStrategy(HashMap<Player, Integer> battleWins) {
        this.battleWinsInit = battleWins;
    }

    @Override
    public Player getWinner(GameImpl game) {
        HashMap<Player, Integer> battleWins = game.getBattleWins();
        for (Map.Entry<Player, Integer> entry : battleWins.entrySet()) {
            Player p = entry.getKey();
            Integer i = entry.getValue();
            int valueAt20;
            if (battleWinsInit.get(p) != null){
                valueAt20 = battleWinsInit.get(p);
            }else{
                valueAt20 = 0;
            }
            if (i >= (3 + valueAt20)) return p;
        }
        return null;
    }
}
