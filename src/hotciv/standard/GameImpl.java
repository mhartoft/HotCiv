package hotciv.standard;

import hotciv.common.AgingStrategy;
import hotciv.common.UnitActionStrategy;
import hotciv.common.WinnerStrategy;
import hotciv.common.WorldLayoutStrategy;
import hotciv.framework.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Skeleton implementation of HotCiv.

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Department of Computer Science
 Aarhus University

 Please visit http://www.baerbak.com/ for further information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

public class GameImpl implements Game {

    private AgingStrategy ageController;
    private WinnerStrategy winnerController;
    private UnitActionStrategy actionStrategy;
    private WorldLayoutStrategy layoutStrategy;
    private HashMap tiles;
    private HashMap units;
    private HashMap<Position, CityImpl> cities;
    private int year;
    private Player playerInTurn;
    private HashMap costs;

    public GameImpl(AgingStrategy chosenAgeStrategy,
                    WinnerStrategy chosenWinnerStrategy,
                    UnitActionStrategy actionStrategy,
                    WorldLayoutStrategy layoutStrategy) {

        tiles = new HashMap<Position, TileImpl>();
        units = new HashMap<Position, UnitImpl>();
        cities = new HashMap<Position, CityImpl>();

        this.layoutStrategy = layoutStrategy;
        layoutStrategy.worldLayout(this);

        this.ageController = chosenAgeStrategy;
        this.winnerController = chosenWinnerStrategy;
        this.actionStrategy = actionStrategy;

        costs = new HashMap();
        costs.put(GameConstants.ARCHER, 10);
        costs.put(GameConstants.LEGION, 15);
        costs.put(GameConstants.SETTLER, 30);

        year = -4000;

        playerInTurn = Player.RED;
    }

    public Tile getTileAt(final Position p ) {
        TileImpl t = (TileImpl) tiles.get(p);
        if (t == null) return new TileImpl(GameConstants.PLAINS);
        return t;
    }
    public Unit getUnitAt( Position p ) {
        return (UnitImpl) units.get(p);
    }
    public City getCityAt( Position p ) { return (CityImpl) cities.get(p); }
    public Player getPlayerInTurn() {
        return playerInTurn;
    }
    public Player getWinner() { return winnerController.getWinner(this); }
    public int getAge() {
        return year;
    }
    public boolean moveUnit( Position from, Position to ) {
        UnitImpl targetUnit = (UnitImpl) getUnitAt(from);
        if (targetUnit == null) return false;
        if (targetUnit.getOwner() != getPlayerInTurn()) return false;
        if (isPositionOccupiedByFriendlyUnit(to, targetUnit.getOwner())) return false;
        if (targetUnit.getFortified()) return false;

        Tile t = getTileAt(to);
        if (t.getTypeString().equals(GameConstants.MOUNTAINS)) return false;
        if (t.getTypeString().equals(GameConstants.OCEANS)) return false;
        if (positionsAreAdjacent(from, to) && isPositionInWorld(to)) {
            units.put(to, getUnitAt(from));
            units.remove(from, getUnitAt(from));
            // if a city exists at end location, change ownership
            CityImpl city = (CityImpl) getCityAt(to);
            if (city != null) city.changeOwner(targetUnit.getOwner());
            return true;
        }
        return false;
    }
    public void endOfTurn() {
        if (getPlayerInTurn() == Player.RED){ // If it's red's turn, it becomes blue's turn
            playerInTurn = Player.BLUE;
        }else{ // otherwise it becomes red's turn
            playerInTurn = Player.RED;
            // Cities accumulate resources
            // And produce if possible
            for (Map.Entry<Position, CityImpl> entry : cities.entrySet()){
                Position p = entry.getKey();
                CityImpl c = entry.getValue();
                c.accumulateResources();
                String target = c.getProduction();
                int targetCost = (Integer) costs.get(target);
                if (c.getResources() >= targetCost){
                    units.put(findAppropriatePositionForNewUnit(p), new UnitImpl(target,c.getOwner()));
                    c.subtractResources(targetCost);
                }
            }
            year = ageController.age(year);
        }
    }
    public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
    public void changeProductionInCityAt( Position p, String unitType ) {}
    public void performUnitActionAt( Position p ) {
        actionStrategy.action(this,p);
    }

    private boolean positionsAreAdjacent(Position from, Position to){
        // Math.abs() gives absolute value (e.g. -10 becomes 10)
        int rowDistance = Math.abs(to.getRow()-from.getRow());
        int colDistance = Math.abs(to.getColumn()-from.getColumn());

        return (rowDistance <= 1 && colDistance <= 1);
    }

    private boolean isPositionInWorld(Position to) {
        if (to.getRow() < 0 ||
                to.getRow() > GameConstants.WORLDSIZE ||
                to.getColumn() < 0 ||
                to.getColumn() > GameConstants.WORLDSIZE)
            return false;
        return true;
    }

    private boolean isPositionOccupiedByFriendlyUnit(Position to, Player owner) {
        UnitImpl unit = (UnitImpl) getUnitAt(to);
        if(unit == null) return false;
        return unit.getOwner() == owner;
    }

    private Position findAppropriatePositionForNewUnit(Position cityPos){
        if (getUnitAt(cityPos) == null) return cityPos;
        ArrayList<Position> testPoss = new ArrayList<Position>();
        testPoss.add(new Position(cityPos.getRow()-1,cityPos.getColumn()));     // N
        testPoss.add(new Position(cityPos.getRow()-1,cityPos.getColumn()+1));   // NE
        testPoss.add(new Position(cityPos.getRow(),cityPos.getColumn()+1));     // E
        testPoss.add(new Position(cityPos.getRow()+1,cityPos.getColumn()+1));   // SE
        testPoss.add(new Position(cityPos.getRow()+1,cityPos.getColumn()));     // S
        testPoss.add(new Position(cityPos.getRow()+1,cityPos.getColumn()-1));   // SW
        testPoss.add(new Position(cityPos.getRow(),cityPos.getColumn()-1));     // W
        testPoss.add(new Position(cityPos.getRow()-1,cityPos.getColumn()-1));   // NW
        for (Position p : testPoss){
            // Goes through the list in the order we added them, so starting from N moving clockwise
            if (getUnitAt(p) == null) return p;
        }
        return null; // what to do if no tile is available?
    }

    public void setAgeController(AgingStrategy ageController) {
        this.ageController = ageController;
    }

    public HashMap<Position, CityImpl> getCities(){
        return cities;
    }

    public void removeUnit(Position p){
        units.remove(p);
    }

    public void createCity(Position p, Player owner){
        cities.put(p,new CityImpl(owner));
    }
    public void createTile(Position p, String type){
        tiles.put(p, new TileImpl(type));
    }
    public void createUnit(Position p, String type, Player owner) {
        units.put(p, new UnitImpl(type, owner));
    }
}