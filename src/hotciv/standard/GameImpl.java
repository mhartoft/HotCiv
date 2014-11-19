package hotciv.standard;

import hotciv.common.*;
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
    private BattleStrategy battleStrategy;
    private HashMap<Position, TileImpl> tiles;
    private HashMap<Position, UnitImpl> units;
    private HashMap<Position, CityImpl> cities;
    private int year;
    private Player playerInTurn;
    private HashMap costs;
    private HashMap<Player, Integer> battleWins;

    public GameImpl(AgingStrategy chosenAgeStrategy,
                    WinnerStrategy chosenWinnerStrategy,
                    UnitActionStrategy actionStrategy,
                    WorldLayoutStrategy layoutStrategy,
                    BattleStrategy battleStrategy) {

        tiles = new HashMap<Position, TileImpl>();
        units = new HashMap<Position, UnitImpl>();
        cities = new HashMap<Position, CityImpl>();
        battleWins = new HashMap<Player, Integer>();

        this.layoutStrategy = layoutStrategy;
        layoutStrategy.worldLayout(this);

        this.ageController = chosenAgeStrategy;
        this.winnerController = chosenWinnerStrategy;
        this.actionStrategy = actionStrategy;
        this.battleStrategy = battleStrategy;

        costs = new HashMap();
        costs.put(GameConstants.ARCHER, 10);
        costs.put(GameConstants.LEGION, 15);
        costs.put(GameConstants.SETTLER, 30);

        year = -4000;

        playerInTurn = Player.RED;
    }

    public TileImpl getTileAt(final Position p ) {
        TileImpl t = (TileImpl) tiles.get(p);
        if (t == null) return new TileImpl(GameConstants.PLAINS);
        return t;
    }
    public UnitImpl getUnitAt( Position p ) {
        return units.get(p);
    }
    public CityImpl getCityAt( Position p ) { return cities.get(p); }
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
        if (!positionsAreAdjacent(from, to)) return false;
        // perform battle if enemy is at to location
        UnitImpl toUnit = getUnitAt(to);
        if (toUnit != null && !toUnit.getOwner().equals(targetUnit.getOwner())){
            boolean battleOutcome = battleStrategy.battle(from, to, this);
            if(!battleOutcome){
                units.remove(from, getUnitAt(from));
                return true;
            }else{
                increaseWins(targetUnit.getOwner());
            }
        }
        units.put(to, getUnitAt(from));
        units.remove(from, getUnitAt(from));
        // if a city exists at end location, change ownership
        CityImpl city = getCityAt(to);
        if (city != null) city.changeOwner(targetUnit.getOwner());
        return true;
    }
    public void endOfTurn() {
        if (getPlayerInTurn() == Player.RED){ // If it's red's turn, it becomes blue's turn
            playerInTurn = Player.BLUE;
        }else{ // otherwise it becomes red's turn
            playerInTurn = Player.RED;
            getWinner(); // synchronizes winner strategies (especially for ZetaCiv)

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
    public void changeProductionInCityAt( Position p, String unitType ) {
        CityImpl c = getCityAt(p);
        c.setProduction(unitType);
    }
    public void performUnitActionAt( Position p ) {
        actionStrategy.action(this,p);
    }

    private boolean positionsAreAdjacent(Position from, Position to){
        // Math.abs() gives absolute value (e.g. -10 becomes 10)
        int rowDistance = Math.abs(to.getRow()-from.getRow());
        int colDistance = Math.abs(to.getColumn()-from.getColumn());

        return (rowDistance <= 1 && colDistance <= 1);
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

    public void increaseWins(Player winner){
        Integer winsBefore = battleWins.get(winner);
        if (winsBefore != null){
            battleWins.put(winner, winsBefore.intValue()+1);
        }else{
            battleWins.put(winner, 1);
        }
    }

    public HashMap<Player, Integer> getBattleWins() { return battleWins; }

    public void resetBattleCounts(){
        battleWins = new HashMap<Player, Integer>();
    }
}