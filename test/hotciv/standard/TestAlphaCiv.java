package hotciv.standard;

import hotciv.framework.*;

import hotciv.variants.AlphaCivAction;
import hotciv.variants.AlphaCivAging;
import hotciv.variants.AlphaCivWinner;
import hotciv.variants.AlphaCivWorldLayout;
import org.junit.*;
import static org.junit.Assert.*;

/** Skeleton class for AlphaCiv test cases 

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
public class TestAlphaCiv {
    private Game game;
    /** Fixture for alphaciv testing. */
    @Before
    public void setUp() {
        game = new GameImpl(new AlphaCivAging(),
                            new AlphaCivWinner(),
                            new AlphaCivAction(),
                            new AlphaCivWorldLayout());
    }

    @Test
    public void shouldHaveRedCityAt1_1() {
        City c = game.getCityAt(new Position(1,1));
        assertNotNull("There should be a city at (1,1)", c);
        Player p = c.getOwner();
        assertEquals( "City at (1,1) should be owned by red",
                Player.RED, p );
    }
    @Test
    public void shouldBeOceanAt1_0(){
        Tile t = game.getTileAt(new Position(1,0));
        assertEquals("The tile at position 1,0 should be of type ocean", GameConstants.OCEANS, t.getTypeString());
    }
    @Test
    public void shouldBeRedsTurnFirst(){
        Player p = game.getPlayerInTurn();
        assertEquals("It should be reds turn first", Player.RED, p);
    }
    @Test
    public void shouldNotBeAbleToMoveOverMountain(){
        game.endOfTurn();
        Boolean move = game.moveUnit(new Position(3,2), new Position(2,2));
        assertEquals("Should not be able to move over mountains", false, move);
    }
    @Test
    public void shouldNotBeAbleToMoveOverOceans(){
        Boolean move = game.moveUnit(new Position (2,0), new Position(1,0));
        assertEquals("Should not be able to move over oceans", false, move);
    }


    @Test
    public void shouldBeRedArcherAt2_0(){
        Unit u = game.getUnitAt(new Position(2,0));
        assertEquals("Should be an archer at 2,0", GameConstants.ARCHER, u.getTypeString());
        assertEquals("Archer should be owned by red at 2,0", Player.RED, u.getOwner());
    }
    @Test
    public void shouldBeRedSettlerAt4_3(){
        Unit u = game.getUnitAt(new Position(4,3));
        assertEquals("Should be a settler at 4,3", GameConstants.SETTLER, u.getTypeString());
        assertEquals("Settler should be owned by red at 4,3", Player.RED, u.getOwner());
    }
    @Test
    public void shouldBePlainAt3_3(){
        Tile t = game.getTileAt(new Position(3, 3));
        assertEquals("Should be plain where no other tile is", GameConstants.PLAINS, t.getTypeString());
    }
    @Test
    public void shouldBeAbleToMoveUnitFrom2_0To2_1(){
        Unit archerBefore = game.getUnitAt(new Position(2,0));
        assertEquals("Should be an archer at 2,0", GameConstants.ARCHER, archerBefore.getTypeString());
        assertEquals("Archer should be owned by red at 2,0", Player.RED, archerBefore.getOwner());

        Boolean b = game.moveUnit(new Position(2,0), new Position(2,1));
        assertEquals("Should return true, as archer has been moved", true, b);

        Unit archerAfter = game.getUnitAt(new Position(2,1));
        assertEquals("Should have moved archer to 2,1", GameConstants.ARCHER, archerAfter.getTypeString());

        Unit unitAfter = game.getUnitAt(new Position(2,0));
        assertNotSame("The same unit should not still be there", archerBefore, unitAfter);
    }

    @Test
    public void shouldBeYear4000BCAtFirst(){
        assertEquals("Year should be 4000 BC from the get-go", -4000, game.getAge());
    }

    @Test
    public void shouldBeYear3900BCAndBluesTurnAfterFirstTurnover(){
        game.endOfTurn();
        assertEquals("Year should be 4000 BC after 1 turn", -4000, game.getAge());
        assertEquals("Should be blue's turn after 1 red turn", Player.BLUE, game.getPlayerInTurn());
    }

    @Test
    public void shouldBeYear3800AndRedsTurnAfter4Turns(){
        helperForEndOfTurns(4);
        assertEquals("Year should be 3800 BC after 4 turns", -3800, game.getAge()); // 4 turns = 2 rounds
        assertEquals("Should be red's turn after 4 turns", Player.RED, game.getPlayerInTurn());
    }

    @Test
    public void shouldHaveARedCityAt1_1(){
        City c = game.getCityAt(new Position(1,1));
        assertEquals("There should be a red city at 1,1", Player.RED, c.getOwner());
    }

    @Test
    public void shouldNotBeAbleToMoveToNonAdjacentInOneMove(){
        boolean move1 = game.moveUnit(new Position(2,0), new Position(8,8));
        boolean move2 = game.moveUnit(new Position(2,0), new Position(4,0));
        boolean move3 = game.moveUnit(new Position(3,2), new Position(1,2));
        assertEquals("Shouldnt be able to move from 2,0 to 8,8", false, move1);
        assertEquals("Shouldnt be able to move from 2,0 to 4,0", false, move2);
        assertEquals("Shouldnt be able to move from 3,2 to 1,2", false, move3);
    }

    @Test
    public void shouldNotBeAbleToMoveOutOfWorld(){
        boolean move = game.moveUnit(new Position(2,0), new Position(2,-1));
        assertEquals("Shouldnt be able to move out of world", false, move);
    }

    @Test
    public void shouldBeReturnCrushedEnemyWhichIsNull(){
        Unit defenseUnit = game.getUnitAt(new Position(3,2));
        // I move the red archer three times, till it ends on the blue legion
        game.moveUnit(new Position(2,0), new Position(2,1));
        game.moveUnit(new Position(2,1), new Position(3,1));
        game.moveUnit(new Position(3,1), new Position(3,2));
        // Critical position is 3,2.
        Unit victoriousUnit = game.getUnitAt(new Position(3,2));
        assertEquals("Owners should be different", false, victoriousUnit.getOwner() == defenseUnit.getOwner());
        assertEquals("Victorious unit should be Red", Player.RED, victoriousUnit.getOwner());
        game.moveUnit(new Position(3,2), new Position(3,3));
        assertEquals("Unit on 3,3 should be owned by Red", Player.RED, game.getUnitAt(new Position(3,3)).getOwner());
        assertEquals("There should not, be no unit at 3,2", null, game.getUnitAt(new Position(3,2)));
    }

    @Test
    public void shouldNotBeAbleToMoveUntoFriendlyUnits(){
        game.moveUnit(new Position(2,0), new Position(2,1));
        game.moveUnit(new Position(2,1), new Position(3,1));
        game.moveUnit(new Position(3,1), new Position(3,2));
        boolean move = game.moveUnit(new Position(3,2), new Position(4,3));
        assertEquals("Should not be able to move the archer unto the settler", false, move);
    }

    @Test
    public void shouldOnlyBeAbleToMoveOwnUnits(){
        boolean move = game.moveUnit(new Position(3,2), new Position(4,2));
        assertEquals("Should not be able to move the blue unit", false, move);
    }

    @Test
    public void shouldNotBeAbleToMoveNothing(){
        boolean move = game.moveUnit(new Position(0,0), new Position(1,1));
        assertEquals("There is no unit at 0,0, and we should not be able to make this move", false, move);
    }

    @Test
    public void shouldGet6ResourcesInCities(){
        helperForEndOfRounds(1);
        CityImpl redCity = (CityImpl) game.getCityAt(new Position(1,1));
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4,1));
        assertEquals("Red city should have 6 resources after 1 round", 6, redCity.getResources());
        assertEquals("Blue city should have 6 resources after 1 round", 6, blueCity.getResources());
    }

    @Test
    public void shouldProduceArcherForCitiesAfter2Rounds(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
        game.changeProductionInCityAt(new Position(4,1), GameConstants.ARCHER);
        helperForEndOfRounds(2);
        UnitImpl redArcher = (UnitImpl) game.getUnitAt(new Position(1,1));
        UnitImpl blueArcher = (UnitImpl) game.getUnitAt(new Position(4,1));
        assertEquals("Red city should have produced an archer after 2 rounds", GameConstants.ARCHER, redArcher.getTypeString());
        assertEquals("Blue city should have produced an archer after 2 rounds", GameConstants.ARCHER, blueArcher.getTypeString());
    }

    @Test
    public void shouldHaveRemovedResourcesFromCitiesAfterProduction(){
        CityImpl redCity = (CityImpl) game.getCityAt(new Position(1,1));
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4,1));
        redCity.setProduction(GameConstants.ARCHER);
        blueCity.setProduction(GameConstants.ARCHER);
        helperForEndOfRounds(2);
        assertEquals("Red city should have 2 resources after 2 rounds + 1 archer prod.", (6*2)-10, redCity.getResources());
        assertEquals("Blue city should have 2 resources after 2 rounds + 1 archer prod.", (6*2)-10, blueCity.getResources());
    }

    @Test
    public void shouldFindSmartPlaceForMultipleUnitsFromSameCity(){
        CityImpl redCity = (CityImpl) game.getCityAt(new Position(1,1));
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4,1));
        redCity.setProduction(GameConstants.ARCHER);
        blueCity.setProduction(GameConstants.SETTLER);
        helperForEndOfRounds(4);
        UnitImpl redUnit = (UnitImpl) game.getUnitAt(new Position(0,1));
        assertEquals("Red has produced 2 archers. The 2nd should be at 0,1", GameConstants.ARCHER, redUnit.getTypeString());
    }

    @Test
    public void shouldMakeRedWinnerAt3000BC(){
        helperForEndOfRounds(10);
        assertEquals("Red Should be the winner at 3000 BC", Player.RED, game.getWinner());
    }

    @Test
    public void shouldNotHaveAWinnerAt3500BC(){
        helperForEndOfRounds(5);
        assertEquals("There should not be a winner at 3500 BC", null, game.getWinner());
    }

    public void helperForEndOfTurns(int n){
        for (int i = 0; i < n; i++){
            game.endOfTurn();
        }
    }
    public void helperForEndOfRounds(int n){
        helperForEndOfTurns(n*2);
    }

}