package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.variants.thetaciv.ThetaCivConstants;

/**
 * Created by mha2908 on 04/11/14.
 */
public class UnitImpl implements Unit {
    private String type;
    private Player owner;
    private int defense;
    private boolean fortified;
    private int attack;
    private int distance;

    public UnitImpl(String type, Player owner) {
        this.type = type;
        this.owner = owner;
        this.fortified = false;
        this.distance = 1;
        if (type.equals(GameConstants.ARCHER)){
            defense = 3;
            attack = 2;
        } else if (type.equals(GameConstants.SETTLER)) {
            defense = 3;
            attack = 0;
        } else if (type.equals(ThetaCivConstants.CHARIOT)) {
            attack = 3;
            defense = 1;
        } else {
            defense = 2;
            attack = 4;
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
        return distance;
    }

    @Override
    public int getDefensiveStrength() {
        return defense;
    }

    @Override
    public int getAttackingStrength() {
        return attack;
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

    public void hasMoved(){
        this.distance -= 1;
    }

    public int getDistanceAllowed(){
        return distance;
    }

    public void resetDistance(){
        this.distance = 1;
    }
}
