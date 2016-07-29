package site.kiselev.usersession;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.kiselev.datastore.Datastore;
import site.kiselev.task.Task;
import site.kiselev.telegram.Reminder;
import site.kiselev.usersession.state.State;

import static site.kiselev.telegram.Reminder.REMINDERS_KEY;

/**
 * Class for UserSession keeping
 */
public class UserSession {
    private final Logger logger = LoggerFactory.getLogger(UserSession.class);

    private String username;
    private State state;


    UserSession(Datastore datastore, String username) {
        logger.trace("Creating new UserSession for user {}", username);
        this.username = username;
        String json = datastore.get(new String[]{username, "tasks"});
        Task task;
        {
            task = Task.fromJSON(json);
            if (task == null) task = new Task(Task.ROOT_ID);
        }
        Task finalTask = task;
        task.setSaver(t -> {
            datastore.set(new String[]{username, "tasks"}, finalTask.toJSON());
            if (t.isReminderSet()) datastore.zAdd(REMINDERS_KEY,
                    t.getReminder(),
                    new Reminder(t.getReminder(), username, "Reminder: " + t.getSubj()).toJSON()
            );
        });
        task.setNotifier((about, t) -> {
            if (about == Task.NotifyAbout.DELETE_REMINDER) {
                datastore.zRem(REMINDERS_KEY,
                        new Reminder(t.getReminder(), username, "Reminder: " + t.getSubj()).toJSON());
            }
        });
        Config config = new Config(finalTask::findByID);
        state = State.initState(config);
    }

    public Result process(String text) {
        logger.trace("Processing with user {}: {}", username, text);

        Pair<State, Result> pair = state.apply(text);
        state = pair.getKey();

        logger.trace("Returning string: {}", pair.getValue());
        return pair.getValue();
    }
}
