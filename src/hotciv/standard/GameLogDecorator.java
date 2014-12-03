package hotciv.standard;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import hotciv.framework.*;

/**
 * Created by mha2908 on 03/12/14.
 */
public class GameLogDecorator implements Game {
    private Game game;
    public GameLogDecorator(Game g){
        game = g;
    }
    @Override
    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    @Override
    public int getAge() {
        return game.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        Player p = getPlayerInTurn();
        Unit u = getUnitAt(from);
        boolean move = game.moveUnit(from, to);
        if (true){
            System.out.println(p.toString() +
                               " moves " +
                               u.getTypeString() +
                               " from " +
                               from.toString() +
                               " to " +
                               to.toString());
        }
        return move;
    }

    @Override
    public void endOfTurn() {
        System.out.println(getPlayerInTurn() + " ends turn");
        game.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        Player pl = getPlayerInTurn();
        System.out.println(pl.toString() +
                           " changes work force focus in city at " +
                           p.toString() +
                           " to " +
                           balance);
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        Player pl = getPlayerInTurn();
        System.out.println(pl.toString() +
                           " changes production in city at " +
                           p.toString() +
                           " to " +
                           unitType);
        game.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        Player pl = getPlayerInTurn();
        Unit u = getUnitAt(p);
        System.out.println(pl.toString() +
                           " performs action with " +
                           u.getTypeString() +
                           " at " +
                           p.toString());
        game.performUnitActionAt(p);
    }
}
