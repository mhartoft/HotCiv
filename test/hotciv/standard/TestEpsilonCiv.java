package hotciv.standard;

import hotciv.common.DiceStrategy;
import hotciv.framework.*;

import hotciv.variants.alphaciv.AlphaCivActionStrategy;
import hotciv.variants.alphaciv.AlphaCivAgingStrategy;
import hotciv.variants.alphaciv.AlphaCivFactory;
import hotciv.variants.alphaciv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.epsilonciv.EpsilonCivBattleStrategy;
import hotciv.variants.epsilonciv.EpsilonCivFactory;
import hotciv.variants.epsilonciv.EpsilonCivWinnerStrategy;
import org.junit.*;

import java.util.HashMap;

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
public class TestEpsilonCiv {
    private GameImpl game;
    /** Fixture for epsilonciv testing. */
    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonCivFactory(new FixedDiceStrategy()));
    }

    @Test
    public void shouldAlwaysReturn1ForFixedDice(){
        DiceStrategy ds = new FixedDiceStrategy();
        assertEquals("Should always return 1 for fixed dice", 1, ds.getValue());
    }

    // testing of the complete random dice is pretty difficult
    // but we are gonna test that it is [1;6]
    // but but! we tried changing strategy to RealDice in Game setup inside @Before
    // and ran the battle tests (all expecting a 1-eyed dice roll) a couple of times
    // and found that the results were unstable

    @Test
    public void shouldShowBetween1And6ForRealDice(){
        DiceStrategy ds = new RealDiceStrategy();
        boolean result = (ds.getValue() >= 1 && ds.getValue() <= 6);
        assertTrue("Real Dice should show between 1 and 6 eyes", result);
    }

    @Test
    public void shouldBeRedArcherAt1_1AfterBattle(){
        game.moveUnit(new Position(2,0), new Position(1,1));
        game.endOfTurn(); // now it's blue's turn
        game.moveUnit(new Position(3,2), new Position(2,1));
        game.moveUnit(new Position(2,1), new Position(1,1));
        Unit endUnit = game.getUnitAt(new Position(1,1));
        assertEquals("Red should win when blue attacks 1,1", Player.RED, endUnit.getOwner());
    }


    @Test
    public void shouldBeBlueLegionAt2_1AfterBattle(){
        game.endOfTurn();
        game.moveUnit(new Position(3,2), new Position(2,1));
        game.endOfTurn();
        game.moveUnit(new Position(2,0), new Position(2,1));
        Unit endUnit = game.getUnitAt(new Position(2,1));
        assertEquals("Blues defending should have won the battle at 2,1 as 2att<2def", Player.BLUE, endUnit.getOwner());
    }

    @Test
    public void shouldBeBlueLegionAt2_1AfterBattle2(){
        game.moveUnit(new Position(2,0), new Position(2,1));
        game.endOfTurn();
        game.moveUnit(new Position(3,2), new Position(2,1));
        Unit endUnit = game.getUnitAt(new Position(2,1));
        assertEquals("Blues attacking unit should win, as 4att>2def", Player.BLUE, endUnit.getOwner());
    }

    @Test
    public void winnerShouldBeNull(){
        Player p = game.getWinner();
        assertNull("ThisShould be null", p);
    }

    @Test
    public void winnerShouldBeRed(){
        game.increaseWins(Player.RED);
        game.increaseWins(Player.RED);
        game.increaseWins(Player.RED);
        Player p = game.getWinner();
        assertEquals("Red Should be the winner after 3 wins", Player.RED, p);
    }

    @Test
    public void shouldIncreaseWinsWhenBattleWon(){
        // we reuse a test where Blue attacks and wins a battle
        shouldBeBlueLegionAt2_1AfterBattle2();
        HashMap<Player, Integer> battleWins = game.getBattleWins();
        assertEquals("Blue should have won 1 battle", 1, battleWins.get(Player.BLUE).intValue());
    }
}