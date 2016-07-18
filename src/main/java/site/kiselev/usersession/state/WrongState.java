package site.kiselev.usersession.state;

import site.kiselev.usersession.Result;

import java.util.List;

/**
 * Wrong State
 */
class WrongState extends State {


    private State callerState;

    WrongState(State callerState) {
        super(callerState.config, callerState.id);
        this.callerState = callerState;
    }

    @Override
    Result getResult() {
        Result result = callerState.getResult();
        List<String> out = result.getOut();
        String[][] keyboard = result.getKeyboard();
        out.add(0, "*Wrong command!*");
        out.add(1, "");
        return new Result(out, keyboard);
    }

}
