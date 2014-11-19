package hotciv.variants.epsilonciv;

import hotciv.common.BattleStrategy;
import hotciv.common.DiceStrategy;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.Utility;

/**
 * Created by mha2908 on 18/11/14.
 */
public class EpsilonCivBattleStrategy implements BattleStrategy {

    private DiceStrategy diceStrategy;

    public EpsilonCivBattleStrategy(DiceStrategy diceStrategy) {
        this.diceStrategy = diceStrategy;
    }

    @Override
    public boolean battle(Position attacker, Position defender, GameImpl gameObj) {
        int attackingUnitStrength,
            defendingUnitStrength,
            attackingUnitTileBonus,
            defendingUnitTileBonus,
            attackingMateBonus,
            defendingMateBonus,
            attackerTotal,
            defenderTotal,
            attackerDice,
            defenderDice;
        UnitImpl attackingUnit, defendingUnit;
        attackingUnit = gameObj.getUnitAt(attacker);
        defendingUnit = gameObj.getUnitAt(defender);
        attackingUnitStrength = attackingUnit.getAttackingStrength();
        defendingUnitStrength = defendingUnit.getDefensiveStrength();
        attackingUnitTileBonus = Utility.getTerrainFactor(gameObj, attacker);
        defendingUnitTileBonus = Utility.getTerrainFactor(gameObj, defender);
        attackingMateBonus = Utility.getFriendlySupport(gameObj, attacker, attackingUnit.getOwner());
        defendingMateBonus = Utility.getFriendlySupport(gameObj, defender, defendingUnit.getOwner());

        attackerTotal = (attackingUnitStrength + attackingMateBonus) * attackingUnitTileBonus;
        defenderTotal = (defendingUnitStrength + defendingMateBonus) * defendingUnitTileBonus;
        attackerDice = diceStrategy.getValue();
        defenderDice = diceStrategy.getValue();

        return (attackerTotal*attackerDice > defenderTotal*defenderDice);
    }
}
