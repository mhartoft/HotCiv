package hotciv.variants.alphaciv;

import hotciv.common.WinnerStrategy;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 11/11/14.
 */
public class AlphaCivWinnerStrategy implements WinnerStrategy {

    @Override
    public Player getWinner(GameImpl gameObj) {
        if (gameObj.getAge() >= -3000){
            return Player.RED;
        }else{
            return null;
        }
    }
}
