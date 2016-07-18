package site.kiselev.datastore;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Redis Datastore
 */
public class RedisDatastoreTest {

    private RedisDatastore rd = new RedisDatastore();


    @Test
    public void get() throws Exception {
        String s = rd.get(new String[]{"test"});
        assertEquals("testvalue", s);
    }

    @Test
    public void set() throws Exception {
        Random r = new Random(System.currentTimeMillis());
        String testString = Integer.toString(r.nextInt(100000));

        rd.set(new String[]{"test", "random"}, "testdata" + testString);
        String s = rd.get(new String[]{"test", "random"});
        assertEquals("testdata" + testString, s);
    }

    @Test
    public void getWrongKey() {
        String s = rd.get(new String[]{"wrong", "key"});
        assertEquals(null, s);
    }

}