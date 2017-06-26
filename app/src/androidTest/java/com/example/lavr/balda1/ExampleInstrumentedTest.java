package com.example.lavr.balda1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.lavr.balda1.components.PlayerInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.lavr.balda1", appContext.getPackageName());
    }

    @Test
    public void testPlayersAddition()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Game g = new Game(appContext);
        Vector<PlayerInfo> players = Game.getPlayers();
        assertTrue(players.size() == 0);

        Game.addPlayer(new PlayerInfo("test1"));
        players = Game.getPlayers();
        assertTrue(players.size() == 1);

        Game.addPlayer(new PlayerInfo("test2"));
        players = Game.getPlayers();
        assertTrue(players.size() == 2);

        Game.deletePlayer("test1");
        players = Game.getPlayers();
        assertTrue(players.size() == 1);

        Game game = new Game(appContext);
        players = Game.getPlayers();
        assertTrue(players.size() == 0);

    }

}
