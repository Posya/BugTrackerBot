package site.kiselev.usersession.state;

import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * Main state
 */
class MainState extends State {


    MainState(Config config) {
        super(config, ROOT_ID);
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        out.add("*Tasks:*");
        out.add("");

        out.addAll(
                config.getTask(ROOT_ID).getSubTasks(config.getShowState()).stream()
                        .map(st ->
                                String.format(
                                        "%s %s /list%d",
                                        (st.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                                        st.getSubj(),
                                        st.getId()
                                ))
                        .collect(Collectors.toList()));

        if (out.size() == 2) out.add("No sub tasks");

        String[][] keyboard = new String[][]{{"/new", "/find", "/reminders"}};
        return new Result(out, keyboard);
    }
}
