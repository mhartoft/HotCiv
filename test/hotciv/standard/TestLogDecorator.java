package hotciv.standard;

import hotciv.framework.*;
import hotciv.variants.gammaciv.GammaCivFactory;
import hotciv.variants.semiciv.SemiCivFactory;
import hotciv.variants.thetaciv.ThetaCivFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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

/*

JUnit setup inspiration:
http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println

Makes our logger log to a ByteArrayOutputStream object so we can test the output

 */
public class TestLogDecorator {
    private Game game;
    private Game decoratee;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Before
    public void setUp() {
        decoratee = new GameImpl(new GammaCivFactory());
        game = new GameLogDecorator(decoratee);
        System.setOut(new PrintStream(outContent));
    }
    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void shouldTranscribeASimpleMove() {
        game.moveUnit(new Position(2,0), new Position (3,0));
        assertEquals("RED moves archer from [2,0] to [3,0]\n", outContent.toString());
    }

    @Test
    public void shouldTranscribeTurnEnd(){
        game.endOfTurn();
        assertEquals("RED ends turn\n", outContent.toString());
    }

    @Test
    public void shouldTranscribeChangeOfWorkFocus(){
        game.changeWorkForceFocusInCityAt(new Position(1,1), "foodFocus");
        assertEquals("RED changes work force focus in city at [1,1] to foodFocus\n", outContent.toString());
    }

    @Test
    public void shouldTranscribeChangeOfProduction(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.LEGION);
        assertEquals("RED changes production in city at [1,1] to legion\n", outContent.toString());
    }

    @Test
    public void shouldTranscribeUnitAction(){
        game.performUnitActionAt(new Position(2,0));
        assertEquals("RED performs action with archer at [2,0]\n", outContent.toString());
    }

    @Test
    public void shouldTurnLoggingOnAndOffDynamically(){
        // This test changes logging dynamically at run(test)-time
        toggleLogging();
        game.performUnitActionAt(new Position(2,0)); // this should not be logged
        assertEquals("", outContent.toString()); // nothing has been written (hopefully)
        toggleLogging();
        game.changeProductionInCityAt(new Position(1,1), GameConstants.LEGION);
        assertEquals("RED changes production in city at [1,1] to legion\n", outContent.toString());
    }

    public void toggleLogging(){
        // if logging is disabled - if our game and decoratee points to same obj
        if (game == decoratee){
            decoratee = game;
            game = new GameLogDecorator(game);
        // if logging is enabled, then set game to point at GameImpl
        // without GameLogDecorator as 'man-in-the-middle'
        }else{
            game = decoratee;
        }
    }

}