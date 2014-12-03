package hotciv.standard;

import hotciv.framework.*;
import hotciv.variants.thetaciv.ThetaCivConstants;
import hotciv.variants.thetaciv.ThetaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class TestThetaCiv {
    private Game game;
    /** Fixture for thetaciv testing. */
    @Before
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory()); // GammaCiv resembles AlphaCiv except for unit actions
    }

    @Test
    public void shouldProduceChariot(){
        game.changeProductionInCityAt(new Position(1,1), ThetaCivConstants.CHARIOT);
        helperForEndOfRounds(4);
        Unit chariot = game.getUnitAt(new Position(1,1));
        assertEquals("Should have produced a red Chariot after 4 rounds", ThetaCivConstants.CHARIOT, chariot.getTypeString());
    }

    @Test
    public void shouldShow1AsChariotDefense(){
        shouldProduceChariot();
        Unit chariot = game.getUnitAt(new Position(1,1));
        assertEquals("Chariots have 1 defense strength", 1, chariot.getDefensiveStrength());
    }

    @Test
    public void shouldShow3AsChariotAttack(){
        shouldProduceChariot();
        Unit chariot = game.getUnitAt(new Position(1,1));
        assertEquals("Chariots have 3 attack strength", 3, chariot.getAttackingStrength());
    }

    @Test
    public void shouldNotProduceAfter3Rounds(){
        // this is a cost checker, as chariot costs 20 and 6*3 < 20
        game.changeProductionInCityAt(new Position(1,1), ThetaCivConstants.CHARIOT);
        helperForEndOfRounds(3);
        Unit chariot = game.getUnitAt(new Position(1,1));
        assertNull("There should not be a new unit (Chariot) in the red city after 3 rounds", chariot);
    }

    @Test
    public void shouldFortifyChariot(){
        shouldProduceChariot();
        game.performUnitActionAt(new Position(1,1));
        Unit u = game.getUnitAt(new Position(1,1));
        assertEquals("Defense should be 2", 2, u.getDefensiveStrength());
        boolean move = game.moveUnit(new Position(1,1), new Position(1,2));
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