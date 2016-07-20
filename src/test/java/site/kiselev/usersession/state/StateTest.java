package site.kiselev.usersession.state;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for abstract class State
 */
public class StateTest {
    @Test
    public void initState() throws Exception {
        State state = State.initState(null);
        assertTrue(state instanceof ListState);
    }


}