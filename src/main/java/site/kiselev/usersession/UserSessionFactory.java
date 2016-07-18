package site.kiselev.usersession;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pengrad.telegrambot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.kiselev.Config;
import site.kiselev.datastore.Datastore;
import site.kiselev.datastore.RedisDatastore;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * Caching factory for @{@link UserSession}
 */
@SuppressWarnings("unused")
public class UserSessionFactory {

    private LoadingCache<String, UserSession> cache;
    private Datastore datastore = new RedisDatastore();

    private final Logger logger = LoggerFactory.getLogger(UserSessionFactory.class);

    public class UserSessionFactoryException extends Exception {
        UserSessionFactoryException(Exception e) {
            super(e);
        }
    }

    public UserSessionFactory() {
        logger.debug("Creating new UserSessionFactory");
        CacheLoader<String, UserSession> loader = new CacheLoader<String, UserSession>() {
            @Override
            public UserSession load(String username) throws Exception {
                return new UserSession(datastore, username);
            }
        };
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(Config.EXPIRE_AFTER_TIMEOUT, Config.EXPIRE_AFTER_TIMEOUT_TIME_UNIT)
                .build(loader);
    }

    public UserSessionFactory(CacheLoader<String, UserSession> otherLoader) {
        logger.debug("Creating new UserSessionFactory with custom loader");
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(Config.EXPIRE_AFTER_TIMEOUT, Config.EXPIRE_AFTER_TIMEOUT_TIME_UNIT)
                .build(otherLoader);
    }

    public  UserSession getUserSession(String username) throws UserSessionFactoryException {
        logger.trace("Getting UserSession for user {}", username);
        try {
            return cache.get(username);
        } catch (ExecutionException e) {
            logger.error("Can't get UserSession for user {}: {}", username, e);
            logger.trace(Arrays.toString(e.getStackTrace()));
            throw new UserSessionFactoryException(e);
        }
    }
}
