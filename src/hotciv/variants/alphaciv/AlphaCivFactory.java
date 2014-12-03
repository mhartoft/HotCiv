package hotciv.variants.alphaciv;

import hotciv.common.*;
import hotciv.standard.GameImpl;
import hotciv.variants.alphaciv.*;

/**
 * Created by mha2908 on 20/11/14.
 */
public class AlphaCivFactory implements HotCivFactory {
    public WinnerStrategy createWinnerStrategy(){return new AlphaCivWinnerStrategy();}
    public AgingStrategy createAgingStrategy(){return new AlphaCivAgingStrategy();}
    public BattleStrategy createBattleStrategy(){return new AlphaCivBattleStrategy();}
    public WorldLayoutStrategy createWorldLayoutStrategy(){return new AlphaCivWorldLayoutStrategy();}
    public UnitActionStrategy createUnitActionStrategy(){return new AlphaCivActionStrategy();}
    public UnitProductionStrategy createUnitProductionStrategy(){return new AlphaCivUnitProductionStrategy();}
}
