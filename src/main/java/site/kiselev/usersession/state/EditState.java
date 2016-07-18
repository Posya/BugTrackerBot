package site.kiselev.usersession.state;

import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Edit task state
 */
class EditState extends State {

    private String subj = null;
    private String desc = null;
    private CurrentState currentState;

    private enum CurrentState {
        SUBJ, DESC, DONE
    }


    EditState(Config config, long id) {
        super(config, id);
        currentState = CurrentState.SUBJ;
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        String[][] keyboard = new String[][]{};
        switch (currentState) {
            case SUBJ:
                out.add("Current subject: " + config.getTask(id).getSubj());
                out.add("Enter new subject:");
                keyboard = new String[][]{{"/cancel", "/skip"}};
                break;
            case DESC:
                out.add("Current description: " + config.getTask(id).getDescription());
                out.add("Enter new description:");
                keyboard = new String[][]{{"/cancel", "/skip"}};
                break;
            case DONE:
                out.add("*Save task:*");
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
                } else if (input.startsWith("/skip")) {
                    subj = config.getTask(id).getSubj();
                    currentState = CurrentState.DESC;
                    newState = this;
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
                    desc = config.getTask(id).getDescription();
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
                    config.getTask(id)
                            .setSubj(subj)
                            .setDescription(desc)
                            .save();
                    newState = new ListState(config, id);
                }
                break;
        }
        return newState;
    }
}
