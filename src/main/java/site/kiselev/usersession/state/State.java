package site.kiselev.usersession.state;

import javafx.util.Pair;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * Class for state keeping
 */
abstract public class State {

    static final String TASK_DONE_SIGN   = "☑";
    static final String TASK_ACTIVE_SIGN = "☐";


    private Pattern p = Pattern.compile("^/([A-Za-z]+)(\\d+)?(\\s+.*)?$");

    final Config config;
    long id;

    State(Config config, long id) {
        this.config = config;
        this.id = id;
    }

    abstract Result getResult();

    /**
     * Returns initial users state
     * @param config    - @{@link Config}
     * @return          - initial state
     */
    public static State initState(Config config) {
        return new ListState(config, ROOT_ID);
    }

    public Pair<State, Result> apply(String input) {
        // Check @input, get new @State and it's parameters
        State newState = this.getNewState(input);
        // Getting @Result from @newState
        Result result = newState.getResult();
        // return
        return new Pair<>(newState, result);
    }

    State getNewState(String input) {

        long cmdID = this.id;

        Matcher m = p.matcher(input);
        if (!m.matches()) {
            return new WrongState(this);
        } else {
            try {
                cmdID = Long.parseLong(m.group(2));
            } catch (NumberFormatException ignored) {}
        }

        if (config.getTask(cmdID) == null) return new WrongState(this);

        String cmd = m.group(1).toLowerCase();
        String args = m.group(3) == null ? "" : m.group(3).trim();

        switch (cmd) {
        // Can be done with id = ROOT_ID
            case "main":
                return new ListState(config, ROOT_ID);
            case "list":
                return new ListState(config, cmdID);
            case "new":
                return new NewState(config, cmdID, args);
            case "reminders":
                return new RemindersState(config);
            case "find":
                return new FindState(config, args);
            case "tree":
                return new TreeState(config, cmdID);
        // Can't be done with id = ROOT_ID
            default:
                if (cmdID == ROOT_ID) return new WrongState(this);
                switch (cmd) {
                    case "detail":
                        return new DetailState(config, cmdID);
                    case "edit":
                        return new EditState(config, cmdID);
                    case "reminder":
                        return new ReminderState(config, cmdID);

                // Do something and returns self
                    default:
                        switch (cmd) {
                            case "done":
                                config.getTask(cmdID).done().save();
                                break;
                            case "undone":
                                config.getTask(cmdID).undone().save();
                                break;
                            case "archive":
                                config.getTask(cmdID).archive().save();
                                break;
                            case "unarchive":
                                config.getTask(cmdID).unarchive().save();
                                break;

                // No matches. Wrong State
                            default:
                                return new WrongState(this);
                        }
                        return this;
                }
        }
    }
}
