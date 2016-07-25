package site.kiselev.usersession.state;

import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrong State
 */
class WrongState extends State {


    WrongState(State callerState) {
        super(callerState.config, callerState.id);
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        out.add(0, "*Wrong command!*");
        return new Result(out, new String[][]{});
    }

}
