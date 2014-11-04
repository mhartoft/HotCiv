package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * Created by mha2908 on 04/11/14.
 */
public class UnitImpl implements Unit {
    private String type;
    private Player owner;

    public UnitImpl(String type, Player owner) {
        this.type = type;
        this.owner = owner;
    }

    @Override
    public String getTypeString() {
        return type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return 0;
    }

    @Override
    public int getDefensiveStrength() {
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }
}
