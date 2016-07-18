package site.kiselev.usersession.state;

import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Task detail state
 */
public class DetailState extends State {

    public DetailState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();

        Task t = config.getTask(id);

        out.add(String.format("*%s* /edit%d", t.getSubj(), t.getId()));
        out.add("");
        out.add(String.format("Description: %s", t.getDescription()));
        out.add(String.format("State: %s /done", t.getState()));
        out.add(String.format("Remind: %s", t.getReminder()));

        String[][] keyboard = new String[][]{{"/main", "/find", "/reminders"}};
        return new Result(out, keyboard);
    }

}
