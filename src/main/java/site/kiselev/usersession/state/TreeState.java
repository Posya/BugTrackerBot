package site.kiselev.usersession.state;

import com.google.common.base.Strings;
import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree State
 */
class TreeState extends State {
    TreeState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {
        Task task = config.getTask(id);
        List<String> out = buildTreeRecursive(task, 0);

        String[][] keyboard = new String[][]{{"/main"}};
        return new Result(out, keyboard);
    }

    private List<String> buildTreeRecursive(Task task, int offset) {
        List<String> result = new ArrayList<>();

        if (task.getId() != Task.ROOT_ID) {
            result.add(String.format("%s %s %s /list%d",
                    Strings.repeat("\t", offset),
                    (task.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                    task.getSubj(),
                    task.getId()
            ));
        } else {
            result.add("*Tasks:*");
        }

        for (Task t : task.getSubTasks()) {
            result.addAll(buildTreeRecursive(t, offset + 1));
        }

        return result;
    }
}
