package hotciv.standard;

import hotciv.framework.*;

import hotciv.variants.alphaciv.*;
import hotciv.variants.gammaciv.GammaCivActionStrategy;
import hotciv.variants.gammaciv.GammaCivFactory;
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
public class TestGammaCiv {
    private Game game;
    /** Fixture for gammaciv testing. */
    @Before
    public void setUp() {
        game = new GameImpl(new GammaCivFactory()); // GammaCiv resembles AlphaCiv except for unit actions
    }

    @Test
    public void shouldTurnSettlerIntoCity(){
        game.performUnitActionAt(new Position(4,3));
        City c = game.getCityAt(new Position(4,3));
        assertEquals("Should be a red city at 4,3", Player.RED,c.getOwner());
    }
    @Test
    public void shouldFortifyArcherAt2_0(){
        game.performUnitActionAt(new Position(2,0));
        Unit u = game.getUnitAt(new Position(2,0));
        assertEquals("Defense should be 6", 6, u.getDefensiveStrength());
        boolean move = game.moveUnit(new Position(2,0), new Position(2,1));
        assertEquals("Should not be able to move fortified archer", false, move);
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