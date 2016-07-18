package site.kiselev.usersession;

import site.kiselev.task.Task;

import java.util.function.Function;

/**
 * Class for global user config keeping
 */
public class Config {

    private Function<Long, Task> taskGetter;

    public Config(Function<Long, Task> taskGetter) {
        this.taskGetter = taskGetter;
    }

    public Task getTask(long id) {
        return taskGetter.apply(id);
    }

}
