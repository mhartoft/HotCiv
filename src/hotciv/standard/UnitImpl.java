package hotciv.standard;

import hotciv.common.UnitActionStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * Created by mha2908 on 04/11/14.
 */
public class UnitImpl implements Unit {
    private String type;
    private Player owner;
    private int defense;
    private boolean fortified;

    public UnitImpl(String type, Player owner) {
        this.type = type;
        this.owner = owner;
        this.fortified = false;
        if (type == GameConstants.ARCHER || type == GameConstants.SETTLER){
            defense = 3;
        } else {
            defense = 2;
        }

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
        return defense;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }

    public void setDefensiveStrength(int i) {
        defense = i;
    }

    public void setFortified(){
        fortified = !fortified;
    }
    public boolean getFortified() {
        return fortified;
    }
}
