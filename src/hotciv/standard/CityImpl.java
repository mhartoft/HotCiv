package hotciv.standard;

import hotciv.framework.*;

/**
 * Created by mha2908 on 04/11/14.
 */
public class CityImpl implements City {
    private Player owner;
    private int size;
    private String production;
    private String workForceFocus;

    public CityImpl(Player owner){
        this.owner = owner;
        this.size = 1;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
}
