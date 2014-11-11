package hotciv.common;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;

/**
 * Created by mha2908 on 11/11/14.
 */
public interface WinnerStrategy {
    public Player getWinner(GameImpl game);
}
