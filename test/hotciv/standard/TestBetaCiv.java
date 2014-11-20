package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.alphaciv.AlphaCivActionStrategy;
import hotciv.variants.alphaciv.AlphaCivBattleStrategy;
import hotciv.variants.alphaciv.AlphaCivFactory;
import hotciv.variants.alphaciv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.betaciv.BetaCivAgingStrategy;
import hotciv.variants.betaciv.BetaCivFactory;
import hotciv.variants.betaciv.BetaCivWinnerStrategy;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by mha2908 on 11/11/14.
 */
public class TestBetaCiv {
    private Game game;
    /** Fixture for betaciv testing. */
    @Before
    public void setUp() {
        game = new GameImpl(new BetaCivFactory());
    }

    @Test
    public void shouldBe3900BCAfter1Round(){
        helperForEndOfRounds(1);
        assertEquals("Should be 3900 BC after 1 round", -3900, game.getAge());
    }
    @Test
    public void shouldBe100BCAfter39Rounds(){
        helperForEndOfRounds(39);
        assertEquals("Should be 100 BC after 39 rounds", -100, game.getAge());
    }
    @Test
    public void shouldBe1BCAfter40Rounds(){
        helperForEndOfRounds(40);
        assertEquals("Should be 1 BC after 40 rounds", -1, game.getAge());
    }
    @Test
    public void shouldBe1ADAfter41Rounds(){
        helperForEndOfRounds(41);
        assertEquals("Should be 1 AD after 41 rounds", 1, game.getAge());
    }
    @Test
    public void shouldBe50ADAfter42Rounds(){
        helperForEndOfRounds(42);
        assertEquals("Should be 60 AD after 42 rounds", 50, game.getAge());
    }
    @Test
    public void shouldBe100ADAfter43Rounds(){
        helperForEndOfRounds(43);
        assertEquals("Should be 100 AD after 43 rounds", 100, game.getAge());
    }
    @Test
    public void shouldBe1775After77Rounds(){
        helperForEndOfRounds(76);
        assertEquals("We guessed that after 76 rounds it would be year 1750", 1750, game.getAge());
        helperForEndOfRounds(1);
        assertEquals("Should be 1775 AD after 77 rounds", 1775, game.getAge());
    }
    @Test
    public void shouldBe1905After83Rounds(){
        helperForEndOfRounds(82);
        assertEquals("We guessed that after 82 rounds it would be year 1900 AD", 1900, game.getAge());
        helperForEndOfRounds(1);
        assertEquals("Should be 1905 AD after 83 rounds", 1905, game.getAge());
    }
    @Test
    public void shouldBe1971AfterXRounds(){
        helperForEndOfRounds(83+(70/5));
        assertEquals("Should be 1971 AD after "+(83+(70/5))+" rounds", 1971, game.getAge());
    }

    @Test
    public void shouldShowRedAsOwnerOfBlueCityAfterAttacking(){
        City blueCity = game.getCityAt(new Position(4,1));
        assertEquals("Blue should own city at 4,1", Player.BLUE, blueCity.getOwner());
        game.moveUnit(new Position(2,0), new Position(3,1));
        game.moveUnit(new Position(3,1), new Position(4,1));
        // now the red archer is standing on what was the blue city
        City city = game.getCityAt(new Position(4,1));
        assertEquals("Red should own what was the blue city", Player.RED, city.getOwner());
    }

    @Test
    public void shouldShowRedAsWinnerAfterTakingOverBlueCity(){
        game.moveUnit(new Position(2,0), new Position(3,1));
        game.moveUnit(new Position(3, 1), new Position(4, 1));
        City redCity = game.getCityAt(new Position(1,1));
        assertEquals("Red should own city at 1,1", Player.RED, redCity.getOwner());
        City redFormerBlueCity = game.getCityAt(new Position(4,1));
        assertEquals("Red should own city at 4,1", Player.RED, redFormerBlueCity.getOwner());
        assertEquals("Red should be winner when blue city has been conquered", Player.RED, game.getWinner());
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
