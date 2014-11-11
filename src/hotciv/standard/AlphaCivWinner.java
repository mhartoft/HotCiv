package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;

/**
 * Created by mha2908 on 11/11/14.
 */
public class AlphaCivWinner implements WinnerStrategy {
    private Game gameObj;

    @Override
    public Player getWinner() {
        if (gameObj.getAge() <= 3000){
            return Player.RED;
        }else{
            return null;
        }
    }

    @Override
    public void attachGame(GameImpl game) {
        this.gameObj = game;
    }
}
