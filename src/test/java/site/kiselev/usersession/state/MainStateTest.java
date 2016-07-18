package site.kiselev.usersession.state;

import org.junit.Before;
import org.junit.Test;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.ConfigTest;
import site.kiselev.usersession.Result;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for Main screen
 */
public class MainStateTest {
    private MainState ms;
    private Config config;


    @Before
    public void setUp() throws Exception {
        config = ConfigTest.getConfig();
        ms = new MainState(config);
    }

    @Test
    public void getResult() throws Exception {
        Result result = ms.getResult();
        List<String> out = result.getOut();
        String[][] keyboard = result.getKeyboard();
        assertEquals("*Tasks:*", out.get(0));
        assertEquals("", out.get(1));
        assertEquals("[ ] Task11 /done11 /list11", out.get(2));
        assertEquals("[ ] Task12 /done12 /list12", out.get(3));
        assertEquals("[ ] Task13 /done13 /list13", out.get(4));
        assertEquals("[ ] Task14 /done14 /list14", out.get(5));
        assertEquals(6, out.size());
        String[][] expected = {{"/new", "/find", "/reminders"}};
        assertTrue(Arrays.deepEquals(expected, keyboard));
    }

    @Test
    public void getNewState() throws Exception {
        State newState = ms.getNewState("/main");
        assertTrue(newState instanceof MainState);
    }

}