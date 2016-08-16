package site.kiselev.textparser;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by posya on 7/25/16.
 */
public class TextParserTest {

    private TextParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new TextParser("write a book next monday morning");
    }

    @Test
    public void getDate() throws Exception {
        assertTrue(new Date(System.currentTimeMillis()).before(parser.getDate()));
    }

    @Test
    public void getSubj() throws Exception {
        assertEquals("write a book ", parser.getSubj());
    }

}