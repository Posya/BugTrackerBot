package site.kiselev.usersession;

import site.kiselev.task.State;
import site.kiselev.task.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Class for global user config keeping
 */
public class Config {

    private Set<State> showState = new HashSet<>();

    private Function<Long, Task> taskGetter;

    public Config(Function<Long, Task> taskGetter) {
        this.taskGetter = taskGetter;
        showState.add(State.ACTIVE);
        showState.add(State.DONE);
    }

    public Task getTask(long id) {
        return taskGetter.apply(id);
    }



    public Set<State> getShowState() {
        return showState;
    }

    public Config setShowState(Set<State> showState) {
        this.showState = showState;
        return this;
    }
}
