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
 * State to set or delete reminder
 */
class ReminderState extends State {
    ReminderState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {
        return null;
    }
}
