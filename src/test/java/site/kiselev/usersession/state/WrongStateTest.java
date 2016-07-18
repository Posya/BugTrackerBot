package site.kiselev.usersession.state;

import org.junit.Before;
import org.junit.Test;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.ConfigTest;
import site.kiselev.usersession.Result;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for a wrong command
 */
public class WrongStateTest {
    private WrongState ws;
    private Config config;
    private State caller;

    @Before
    public void setUp() throws Exception {
        config = ConfigTest.getConfig();
        caller = new DumbState(config, 0);
        ws = new WrongState(caller);
    }

    @Test
    public void getResult() throws Exception {
        Result result = ws.getResult();
        List<String> out = result.getOut();
        String[][] keyboard = result.getKeyboard();
        assertEquals("*Wrong command!*", out.get(0));
        assertEquals("", out.get(1));
        assertEquals(7, out.size());
    }

    @Test
    public void getNewStateTest() throws Exception {
        { State newState = caller.getNewState("asdfadsfadsf");
          assertTrue(newState instanceof WrongState); }

          { State newState = caller.getNewState("/wrong5 asdfasdf");
          assertTrue(newState instanceof WrongState); }
    }


}