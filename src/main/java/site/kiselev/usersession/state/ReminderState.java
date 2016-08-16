package site.kiselev.usersession.state;

import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * State to set or delete reminder
 */
class ReminderState extends State {
    ReminderState(Config config, long id, String args) {
        super(config, id);
        long t = 0;
        try {
            t = Long.parseLong(args);
        } catch (NumberFormatException ignored) {}
        Task task = config.getTask(id);
        long timestamp = new Date().getTime();
        task.setReminder(timestamp + t);
        task.save();
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        out.add("Ok.");
        long timestamp = new Date().getTime();
        long reminder = config.getTask(id).getReminder();
        out.add("Expected: " + (reminder - timestamp) + " seconds");
        String[][] keyboard = {{"/list", "/detail"}};
        return new Result(out, keyboard);
    }
}
