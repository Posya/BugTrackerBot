package site.kiselev.usersession.state;

import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

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
