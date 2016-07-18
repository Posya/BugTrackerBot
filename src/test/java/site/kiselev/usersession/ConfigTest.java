package site.kiselev.usersession;

import org.junit.Before;
import site.kiselev.task.Task;

import java.util.function.Function;

/**
 * Config Test
 */
public class ConfigTest {
    @Before
    public void setUp() throws Exception {

    }

    public static Config getConfig() {
        Task task = new Task(1)
                .setSubj("Task1")
                .setDescription("Description")
                .setReminder(12345);
        for (int i = 11; i < 15; i++) task.addSubTask(new Task(i).setSubj("Task" + i));
        Function<Long, Task> taskGetter = t -> task;
        return new Config(taskGetter);
    }

}