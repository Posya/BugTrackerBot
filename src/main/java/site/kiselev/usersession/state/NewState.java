package site.kiselev.usersession.state;

import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * New Task Creating
 */
class NewState extends State {


    private String subj = null;
    private String desc = null;
    private CurrentState currentState;

    private enum CurrentState {
        SUBJ, DESC, DONE
    }

    NewState(Config config, long parentID, String subj) {
        super(config, parentID);
        currentState = CurrentState.SUBJ;
        if (subj != null && subj.length() > 0) {
            this.subj = subj;
            currentState = CurrentState.DESC;
        }
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        String[][] keyboard = new String[][]{};
        switch (currentState) {
            case SUBJ:
                out.add("Enter subject:");
                keyboard = new String[][]{{"/cancel"}};
                break;
            case DESC:
                out.add("Enter description:");
                keyboard = new String[][]{{"/cancel", "/skip"}};
                break;
            case DONE:
                out.add("*New task:*");
                out.add("*Subject:* " + subj);
                out.add("*Description:* " + desc);
                out.add("OK?");
                keyboard = new String[][]{{"/ok", "/cancel"}};
                break;
        }
        return new Result(out, keyboard);
    }

    @Override
    State getNewState(String input) {
        State newState = null;

        switch (currentState) {
            case SUBJ:
                if (input.startsWith("/cancel")) {
                    newState = new ListState(config, id);
                } else {
                    subj = input;
                    currentState = CurrentState.DESC;
                    newState = this;
                }
                break;
            case DESC:
                if (input.startsWith("/cancel")) {
                    newState = new ListState(config, id);
                } else if (input.startsWith("/skip")) {
                    desc = "";
                    currentState = CurrentState.DONE;
                    newState = this;
                } else {
                    desc = input;
                    currentState = CurrentState.DONE;
                    newState = this;
                }
                break;
            case DONE:
                if (input.startsWith("/cancel")) {
                    newState = new ListState(config, id);
                } else if (input.startsWith("/ok")) {
                    long timestamp = new Date().getTime();

                    config.getTask(id).addSubTask(
                            new Task(timestamp).setSubj(subj).setDescription(desc)
                    ).save();
                    newState = new ListState(config, id);
                }
                break;
        }
        return newState;
    }
}
