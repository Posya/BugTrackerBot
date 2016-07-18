package site.kiselev.usersession.state;

import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * Find State
 */
class FindState extends State {
    private String query;

    FindState(Config config, String query) {
        super(config, ROOT_ID);
        this.query = query;
    }

    @Override
    Result getResult() {
        List<Task> tasks = config.getTask(ROOT_ID).findByName(query);

        List<String> out = new ArrayList<>();

        out.add(String.format("*Found: %d*", tasks.size()));
        out.add("");
        out.addAll(
                tasks.stream()
                        .map(st ->
                                String.format(
                                        "%s %s /detail%d /list%d",
                                        (st.getState() == site.kiselev.task.State.DONE ? TASK_DONE_SIGN : TASK_ACTIVE_SIGN),
                                        st.getSubj(),
                                        st.getId(),
                                        st.getId()))
                        .collect(Collectors.toList()));

        String[][] keyboard = new String[][]{{"/main"}};
        return new Result(out, keyboard);
    }
}
