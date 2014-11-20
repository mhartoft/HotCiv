package hotciv.standard;

import hotciv.framework.*;

import hotciv.variants.alphaciv.*;
import hotciv.variants.zetaciv.ZetaCivFactory;
import hotciv.variants.zetaciv.ZetaCivWinnerStrategy;
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
public class TestZetaCiv {
    private GameImpl game;
    /** Fixture for epsilonciv testing. */
    @Before
    public void setUp() {
        game = new GameImpl(new ZetaCivFactory());
    }

    @Test // copied from TestBetaCiv
    public void shouldShowRedAsWinnerAfterTakingOverBlueCity(){
        game.moveUnit(new Position(2,0), new Position(3,1));
        game.moveUnit(new Position(3, 1), new Position(4, 1));
        City redCity = game.getCityAt(new Position(1,1));
        assertEquals("Red should own city at 1,1", Player.RED, redCity.getOwner());
        City redFormerBlueCity = game.getCityAt(new Position(4,1));
        assertEquals("Red should own city at 4,1", Player.RED, redFormerBlueCity.getOwner());
        assertEquals("Red should be winner when blue city has been conquered", Player.RED, game.getWinner());
    }

    // Next two are copied from TestEpsilonCiv, but we increase rounds by 20
    @Test
    public void winnerShouldBeNull(){
        helperForEndOfRounds(20);
        Player p = game.getWinner();
        assertNull("ThisShould be null", p);
    }

    @Test
    public void winnerShouldBeRed(){
        helperForEndOfRounds(20);
        game.increaseWins(Player.RED);
        game.increaseWins(Player.RED);
        game.increaseWins(Player.RED);
        Player p = game.getWinner();
        assertEquals("Red Should be the winner after 3 wins", Player.RED, p);
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