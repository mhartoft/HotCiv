package hotciv.variants.semiciv;

import hotciv.common.*;
import hotciv.standard.RealDiceStrategy;
import hotciv.variants.betaciv.BetaCivAgingStrategy;
import hotciv.variants.deltaciv.DeltaCivWorldLayoutStrategy;
import hotciv.variants.epsilonciv.EpsilonCivBattleStrategy;
import hotciv.variants.epsilonciv.EpsilonCivWinnerStrategy;
import hotciv.variants.gammaciv.GammaCivActionStrategy;
import hotciv.variants.thetaciv.ThetaCivUnitProductionStrategy;

/**
 * Created by mha2908 on 26/11/14.
 */
public class SemiCivFactory implements HotCivFactory {
    private String[] s;

    public SemiCivFactory(String[] s) {this.s = s;}

    @Override
    public WinnerStrategy createWinnerStrategy() {return new EpsilonCivWinnerStrategy();}

    @Override
    public AgingStrategy createAgingStrategy() {return new BetaCivAgingStrategy();}

    @Override
    public BattleStrategy createBattleStrategy() {return new EpsilonCivBattleStrategy(new RealDiceStrategy());}

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {return new DeltaCivWorldLayoutStrategy(s);}

    @Override
    public UnitActionStrategy createUnitActionStrategy() {return new GammaCivActionStrategy();}

    @Override
    public UnitProductionStrategy createUnitProductionStrategy() {return new ThetaCivUnitProductionStrategy();}
}
