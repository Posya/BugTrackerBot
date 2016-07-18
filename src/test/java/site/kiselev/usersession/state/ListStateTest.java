package site.kiselev.usersession.state;

import org.junit.Before;
import org.junit.Test;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.ConfigTest;
import site.kiselev.usersession.Result;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static site.kiselev.task.Task.ROOT_ID;

/**
 * Tests for List State
 */
public class ListStateTest {
    private Config config;
    private ListState ls;

    @Before
    public void setUp() throws Exception {
        config = ConfigTest.getConfig();
        ls = new ListState(config, ROOT_ID);
    }

    @Test
    public void getResult() throws Exception {
        Result result = ls.getResult();
        List<String> out = result.getOut();
        String[][] keyboard = result.getKeyboard();
        assertEquals("*Task1* /detail1", out.get(0));
        assertEquals("", out.get(1));
        assertEquals("☐ Task11 /list11", out.get(2));
        assertEquals("☐ Task12 /list12", out.get(3));
        assertEquals("☐ Task13 /list13", out.get(4));
        assertEquals("☐ Task14 /list14", out.get(5));
        assertEquals(6, out.size());
        String[][] expected = {{"/new", "/find", "/reminders"},{"/main"}};
        assertTrue(Arrays.deepEquals(expected, keyboard));
    }

    @Test
    public void getNewState() throws Exception {
        State newState = ls.getNewState("/list1");
        assertTrue(newState instanceof ListState);
    }

}