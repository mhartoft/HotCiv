            package hotciv.standard;

            import hotciv.framework.*;

            import java.util.ArrayList;
            import java.util.HashMap;

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
                private HashMap cities;
                private int yearBC;
                private Player playerInTurn;

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

                    yearBC = 4000;

                    playerInTurn = Player.RED;
                }

              public Tile getTileAt(final Position p ) {
                  TileImpl t = (TileImpl) tiles.get(p);
                  if (t == null) return new TileImpl(GameConstants.PLAINS);
                  return (TileImpl) tiles.get(p);
              }
              public Unit getUnitAt( Position p ) {
                  return (UnitImpl) units.get(p);
              }
              public City getCityAt( Position p ) { return (CityImpl) cities.get(p); }
              public Player getPlayerInTurn() {
                  return playerInTurn;
              }
              public Player getWinner() { return null; }
              public int getAge() {
                  return yearBC;
              }
              public boolean moveUnit( Position from, Position to ) {
                UnitImpl targetUnit = (UnitImpl) units.get(from);
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
                      yearBC -= 100; // The world ages 100 years
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
              else return true;
              }

              private boolean isPositionOccupiedByFriendlyUnit(Position to, Player owner) {
                  UnitImpl unit = (UnitImpl) units.get(to);
                  if(unit == null) return false;
                    return unit.getOwner() == owner;
              }
            }
