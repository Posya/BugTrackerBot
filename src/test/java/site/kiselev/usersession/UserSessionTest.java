package site.kiselev.usersession;

import org.junit.Before;
import org.junit.Test;
import site.kiselev.datastore.Datastore;
import site.kiselev.datastore.DumbDatastore;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for User Session
 */
public class UserSessionTest {
    private UserSession us;

    @Before
    public void setUp() throws Exception {
        Datastore datastore = new DumbDatastore();
        us = new UserSession(datastore, "TestUser");
    }

    @Test
    public void process() throws Exception {
        {   // Main
            Result result = us.process("/main");
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            String[][] expected = {{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));
        }

        {   // List
            Result result = us.process("/list");
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            String[][] expected = {{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));
        }

        {   //Main
            Result result = us.process("/main");
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            String[][] expected = {{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            // New
            result = us.process("/new");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("Enter subject:", out.get(0));
            expected = new String[][]{{"/cancel"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("Subject");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("Enter description:", out.get(0));
            expected = new String[][]{{"/cancel", "/skip"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("Description");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*New task:*", out.get(0));
            assertEquals("*Subject:* Subject", out.get(1));
            assertEquals("*Description:* Description", out.get(2));
            expected = new String[][]{{"/ok", "/cancel"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("/ok");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            Pattern p = Pattern.compile(".*\\D(\\d+)\\D.*");
            Matcher m = p.matcher(out.get(2));
            assertTrue(m.matches());
            long newID = Long.parseLong(m.group(1));
            expected = new String[][]{{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            // Detail
            result = us.process("/detail" + newID);
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*Subject* /edit" + newID, out.get(0));
            assertEquals("", out.get(1));
            assertEquals("Description: Description", out.get(2));
            assertEquals("State: ACTIVE /done", out.get(3));
            assertEquals("Remind: 0", out.get(4));
            assertEquals(5, out.size());
            expected = new String[][]{{"/main", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));
        }

        {   // Main
            Result result = us.process("/main");
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            String[][] expected = {{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            // New
            result = us.process("/new");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("Enter subject:", out.get(0));
            expected = new String[][]{{"/cancel"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("Subject");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("Enter description:", out.get(0));
            expected = new String[][]{{"/cancel", "/skip"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("/skip");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*New task:*", out.get(0));
            assertEquals("*Subject:* Subject", out.get(1));
            assertEquals("*Description:* ", out.get(2));
            expected = new String[][]{{"/ok", "/cancel"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("/cancel");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            expected = new String[][]{{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));
        }

        {   // Main
            Result result = us.process("/main");
            List<String> out = result.getOut();
            String[][] keyboard = result.getKeyboard();
            assertEquals("*Tasks:*", out.get(0));
            assertEquals("", out.get(1));
            Pattern p = Pattern.compile(".*\\D(\\d+)\\D.*");
            Matcher m = p.matcher(out.get(2));
            assertTrue(m.matches());
            long newID = Long.parseLong(m.group(1));
            String[][] expected = {{"/new", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            // Detail
            result = us.process("/detail" + newID);
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*Subject* /edit" + newID, out.get(0));
            assertEquals("", out.get(1));
            assertEquals("Description: Description", out.get(2));
            assertEquals("State: ACTIVE /done", out.get(3));
            assertEquals("Remind: 0", out.get(4));
            assertEquals(5, out.size());
            expected = new String[][]{{"/main", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            // Edit
            result = us.process("/edit" + newID);
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("Current subject: Subject", out.get(0));
            assertEquals("Enter new subject:", out.get(1));
            expected = new String[][]{{"/cancel", "/skip"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("New Subject");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("Current description: Description", out.get(0));
            assertEquals("Enter new description:", out.get(1));
            expected = new String[][]{{"/cancel", "/skip"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("New Description");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*Save task:*", out.get(0));
            assertEquals("*Subject:* New Subject", out.get(1));
            assertEquals("*Description:* New Description", out.get(2));
            expected = new String[][]{{"/ok", "/cancel"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            result = us.process("/ok");
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*New Subject* /detail" + newID, out.get(0));
            assertEquals("", out.get(1));
            p = Pattern.compile(".*\\D(\\d+)$");
            m = p.matcher(out.get(0));
            assertTrue(m.matches());
            newID = Long.parseLong(m.group(1));
            expected = new String[][]{{"/new", "/find", "/reminders"},{"/main"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));

            // Detail
            result = us.process("/detail" + newID);
            out = result.getOut();
            keyboard = result.getKeyboard();
            assertEquals("*New Subject* /edit" + newID, out.get(0));
            assertEquals("", out.get(1));
            assertEquals("Description: New Description", out.get(2));
            assertEquals("State: ACTIVE /done", out.get(3));
            assertEquals("Remind: 0", out.get(4));
            assertEquals(5, out.size());
            expected = new String[][]{{"/main", "/find", "/reminders"}};
            assertTrue(Arrays.deepEquals(expected, keyboard));
        }

    }


}