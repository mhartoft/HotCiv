package hotciv.standard;

import hotciv.framework.*;

import hotciv.variants.*;
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
public class TestDeltaCiv {
    private Game game;
    /** Fixture for gammaciv testing. */
    @Before
    public void setUp() {
        String[] layout =
                new String[] {
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        game = new GameImpl(new AlphaCivAging(),
                new AlphaCivWinner(),
                new AlphaCivAction(),
                new DeltaCivWorldLayoutStrategy(layout));
    }

    @Test
    public void shouldBeOceanAt0_0(){
        Tile t = game.getTileAt(new Position(0,0));
        assertEquals("Should be ocean at 0,0", GameConstants.OCEANS, t.getTypeString());
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