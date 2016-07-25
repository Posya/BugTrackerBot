package site.kiselev.telegram;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Small class for Reminder keeping
 */
@SuppressWarnings("WeakerAccess")
public class Reminder {
    public static final String[] REMINDERS_KEY = {"reminders"};
    private final long timestamp;
    private final String userName;
    private final String message;

    public Reminder(long timestamp, String userName, String message) {
        this.timestamp = timestamp;
        this.userName = userName;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public static Reminder fromJSON(String json) throws JsonSyntaxException {
        // json can be null if we can't read such key
        if (json == null) return null;

        Gson gson = new Gson();
        Reminder result;
        try {
            result = gson.fromJson(json, Reminder.class);
        } catch (JsonSyntaxException | NullPointerException e) {
            throw new JsonSyntaxException("Error in JSON parsing", e);
        }

        return result;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "timestamp=" + timestamp +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
