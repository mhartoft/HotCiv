package hotciv.standard;

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
    private HashMap tiles;
    private HashMap units;
    private HashMap<Position, CityImpl> cities;
    private int yearBC;
    private Player playerInTurn;
    private HashMap costs;
    private Player winner;

    public GameImpl() {
        tiles = new HashMap();
        tiles.put(new Position(2,2), new TileImpl(GameConstants.MOUNTAINS));
        tiles.put(new Position(1,0), new TileImpl(GameConstants.OCEANS));
        tiles.put(new Position(0,1), new TileImpl(GameConstants.HILLS));

        units = new HashMap();
        units.put(new Position(3,2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        units.put(new Position(2,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        units.put(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));

        cities = new HashMap();
        cities.put(new Position(1,1), new CityImpl(Player.RED));
        cities.put(new Position(4,1), new CityImpl(Player.BLUE));

        costs = new HashMap();
        costs.put(GameConstants.ARCHER, 10);
        costs.put(GameConstants.LEGION, 15);
        costs.put(GameConstants.SETTLER, 30);

        yearBC = 4000;
        winner = null;

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
    public Player getWinner() { return winner;}
    public int getAge() {
        return yearBC;
    }
    public boolean moveUnit( Position from, Position to ) {
        UnitImpl targetUnit = (UnitImpl) getUnitAt(from);
        if (targetUnit == null) return false;
        if (targetUnit.getOwner() != getPlayerInTurn()) return false;
        if (isPositionOccupiedByFriendlyUnit(to, targetUnit.getOwner())) return false;

        Tile t = getTileAt(to);
        if (t.getTypeString() == GameConstants.MOUNTAINS) return false;
        if (t.getTypeString() == GameConstants.OCEANS) return false;
        if (positionsAreAdjacent(from, to) && isPositionInWorld(to)) {
            units.put(to, getUnitAt(from));
            units.remove(from, getUnitAt(from));
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

            yearBC -= 100; // The world ages 100 years
            if (yearBC <= 3000)winner = Player.RED;
        }
    }
    public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
    public void changeProductionInCityAt( Position p, String unitType ) {}
    public void performUnitActionAt( Position p ) {}

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
}