package site.kiselev.usersession.state;

import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

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
        return null;
    }
}
