package hotciv.variants.alphaciv;

import hotciv.common.WorldLayoutStrategy;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.framework.*;

/**
 * Created by mha2908 on 11/11/14.
 */
public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {

    @Override
    public void worldLayout(GameImpl gameObj) {
        gameObj.createTile(new Position(2,2), GameConstants.MOUNTAINS);
        gameObj.createTile(new Position(1,0), GameConstants.OCEANS);
        gameObj.createTile(new Position(0,1), GameConstants.HILLS);

        gameObj.createUnit(new Position(3,2), GameConstants.LEGION, Player.BLUE);
        gameObj.createUnit(new Position(2,0), GameConstants.ARCHER, Player.RED);
        gameObj.createUnit(new Position(4,3), GameConstants.SETTLER, Player.RED);

        gameObj.createCity(new Position(1,1), Player.RED);
        gameObj.createCity(new Position(4,1), Player.BLUE);

    }
}

/*

        tiles.put(new Position(2,2), new TileImpl(GameConstants.MOUNTAINS));
        tiles.put(new Position(1,0), new TileImpl(GameConstants.OCEANS));
        tiles.put(new Position(0,1), new TileImpl(GameConstants.HILLS));

        units = new HashMap();
        units.put(new Position(3,2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        units.put(new Position(2,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        units.put(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));

        cities = new HashMap();
        createCity(new Position(1, 1), Player.RED);
        createCity(new Position(4, 1), Player.BLUE);*/
