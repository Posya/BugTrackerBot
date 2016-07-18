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
 * Tests for DetailStateTest
 */
public class DetailStateTest {
    private Config config;
    private DetailState ds;

    @Before
    public void setUp() throws Exception {
        config = ConfigTest.getConfig();
        ds = new DetailState(config, 1);
    }

    @Test
    public void getResult() throws Exception {
        Result result = ds.getResult();
        List<String> out = result.getOut();
        String[][] keyboard = result.getKeyboard();
        assertEquals("*Task1* /edit1", out.get(0));
        assertEquals("", out.get(1));
        assertEquals("Description: Description", out.get(2));
        assertEquals("State: ACTIVE /done", out.get(3));
        assertEquals("Remind: 12345", out.get(4));
        assertEquals(5, out.size());
        String[][] expected = {{"/main", "/find", "/reminders"}};
        assertTrue(Arrays.deepEquals(expected, keyboard));
    }

    @Test
    public void getNewState() throws Exception {
        State newState = ds.getNewState("/detail1");
        assertTrue(newState instanceof DetailState);
    }

}