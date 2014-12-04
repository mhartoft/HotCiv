package hotciv.standard;

import hotciv.framework.*;

import hotciv.variants.alphaciv.*;
import hotciv.variants.deltaciv.DeltaCivFactory;
import hotciv.variants.deltaciv.DeltaCivWorldLayoutStrategy;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * This is more of a driver-class for the fractalmap
 * to test if it prints out something that looks like a map

 */
public class TestFractalMap {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new FractalMapFactoryStub());
    }

    @Test
    public void something(){
        assertTrue(true);
    }

}