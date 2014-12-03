package hotciv.common;

import hotciv.variants.alphaciv.*;

/**
 * Created by mha2908 on 20/11/14.
 */
public interface HotCivFactory {
    public WinnerStrategy createWinnerStrategy();
    public AgingStrategy createAgingStrategy();
    public BattleStrategy createBattleStrategy();
    public WorldLayoutStrategy createWorldLayoutStrategy();
    public UnitActionStrategy createUnitActionStrategy();
    public UnitProductionStrategy createUnitProductionStrategy();
}
