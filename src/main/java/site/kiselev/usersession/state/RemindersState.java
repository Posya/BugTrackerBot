package site.kiselev.usersession.state;

import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * List of Reminders
 */
class RemindersState extends State {
    RemindersState(Config config) {
        super(config, ROOT_ID);
    }

    @Override
    Result getResult() {
        Task task = config.getTask(id);
        List<String> out = new ArrayList<>();

        out.add("*Reminders:*");
        out.add("");
        out.addAll(
                task.getReminders().values().stream()
                        .map(t ->
                                String.format(
                                        "%s %s /list%d",
                                        (t.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                                        t.getSubj(),
                                        t.getId()))
                        .collect(Collectors.toList()));

        if (out.size() == 2) out.add("No reminders");

        String[][] keyboard;
        if (id == ROOT_ID) {
            keyboard = new String[][]{{"/find"}};
        } else {
            keyboard = new String[][]{{"/find"},{"/main"}};
        }
        return new Result(out, keyboard);
    }
}
