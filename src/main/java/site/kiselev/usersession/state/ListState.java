package site.kiselev.usersession.state;

import com.google.common.base.Strings;
import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * List State implementation
 */
class ListState extends State {

    ListState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {
        Task task = config.getTask(id);
        List<String> out = new ArrayList<>();

        if (id == ROOT_ID) {
            out.add("*Tasks:*");
        } else {
            List<Task> parents = task.getParents();
            int i = 1;
            out.add("*Tasks:* /main");
            for (Task t : parents) {
                out.add(String.format("%s %s %s /list%d",
                        Strings.repeat("`     `", i - 1) + SUB_TASK_SIGN,
                        (t.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                        t.getSubj(),
                        t.getId()));
                i++;
            }

            //out.add(String.format("*%s* /detail", task.getSubj()));
        }

        out.add("");
        out.addAll(
                task.getSubTasks(config.getShowState()).stream()
                        .map(st ->
                                String.format(
                                        "%s %s /list%d",
                                        (st.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                                        st.getSubj(),
                                        st.getId()))
                        .collect(Collectors.toList()));

        if (out.size() == 2) out.add("No sub tasks");

        String[][] keyboard;
        if (id == ROOT_ID) {
            keyboard = new String[][]{{"/new", "/find", "/reminders"}};
        } else {
            keyboard = new String[][]{{"/new", "/find", "/reminders"},{"/main"}};
        }
        return new Result(out, keyboard);
    }

}
