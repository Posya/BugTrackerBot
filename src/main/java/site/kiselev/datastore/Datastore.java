package site.kiselev.datastore;

/**
 * {@link Datastore} interface
 */
public interface Datastore {

    String get(String[] key);

    void set(String[] key, String value);

}
