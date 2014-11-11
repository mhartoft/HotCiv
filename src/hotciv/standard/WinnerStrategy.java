package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;

/**
 * Created by mha2908 on 11/11/14.
 */
public interface WinnerStrategy {
    public Player getWinner();
    public void attachGame(GameImpl game);
}
