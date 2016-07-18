package site.kiselev.datastore;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/**
 * Redis DB implementation of Datastore interface
 */
public class RedisDatastore implements Datastore {

    private JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");


    @Override
    public String get(String[] key) {
        String result = "";
        if (isArgsCorrect(key)) {
            String stringKey = String.join(":", (CharSequence[]) key);
            try (Jedis jedis = pool.getResource()) {
                result = jedis.get(stringKey);
            }
        }
        return result;
    }

    @Override
    public void set(String[] key, String value) {
        if (isArgsCorrect(key)) {
            String stringKey = String.join(":", (CharSequence[]) key);
            try (Jedis jedis = pool.getResource()) {
                jedis.set(stringKey, value);
            }
        }
    }

    private boolean isArgsCorrect(String[] key) {
        return !Arrays.asList(key).parallelStream()
                .anyMatch(s -> s.contains(":"));
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        pool.destroy();
    }
}
