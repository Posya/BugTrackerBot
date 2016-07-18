package site.kiselev.usersession.state;

import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        out.add(String.format("*%s* /detail%d", task.getSubj(), task.getId()));
        out.add("");
        out.addAll(
                task.getSubTasks().stream()
                        .map(st ->
                                String.format(
                                        "%s %s /done%d /list%d",
                                        (st.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                                        st.getSubj(),
                                        st.getId(),
                                        st.getId()))
                        .collect(Collectors.toList()));

        if (out.size() == 2) out.add("No sub tasks");

        String[][] keyboard = new String[][]{{"/new", "/find", "/reminders"},{"/main"}};
        return new Result(out, keyboard);
    }

}
