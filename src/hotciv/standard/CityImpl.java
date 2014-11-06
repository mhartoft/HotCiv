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
    private int resources;

    public CityImpl(Player owner){
        this.owner = owner;
        this.size = 1;
        this.resources = 0;
        this.production = GameConstants.ARCHER;
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
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    public int getResources() { return resources; }

    public void accumulateResources(){
        resources += 6;
    }

    public void setProduction(String production){
        this.production = production;
    }

    public void subtractResources(int n){
        this.resources -= n;
    }
}
