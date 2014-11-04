package hotciv.standard;

import hotciv.framework.*;

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
        game = new GameImpl();
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
        Boolean move = game.moveUnit(new Position(1,1), new Position(2,2));
        assertEquals("Should not be able to move over mountains", false, move);
    }
    @Test
    public void shouldNotBeAbleToMoveOverOceans(){
        Boolean move = game.moveUnit(new Position (0,0), new Position(1,0));
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
        assertEquals("Year should be 4000 BC from the get-go", 4000, game.getAge());
    }

    @Test
    public void shouldBeYear3900BCAndBluesTurnAfterFirstTurnover(){
        game.endOfTurn();
        assertEquals("Year should be 3900 BC after 1 turn", 3900, game.getAge());
        assertEquals("Should be blue's turn after 1 red turn", Player.BLUE, game.getPlayerInTurn());
    }

    @Test
    public void shouldBeYear3600AndRedsTurnAfter4Turns(){
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertEquals("Year should be 3600 BC after 4 turns", 3600, game.getAge());
        assertEquals("Should be red's turn after 4 turns", Player.RED, game.getPlayerInTurn());
    }

    @Test
    public void shouldHaveARedCityAt1_1(){
        City c = game.getCityAt(new Position(1,1));
        assertEquals("There should be a red city at 1,1", Player.RED, c.getOwner());
    }

}