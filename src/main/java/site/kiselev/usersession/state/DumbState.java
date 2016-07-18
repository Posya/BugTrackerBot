package site.kiselev.usersession.state;

import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Dumb State
 */
public class DumbState extends State {
    DumbState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < 5; i++) out.add("line" + i);
        String[][] keyboard = new String[][]{};
        return new Result(out, keyboard);
    }
}
