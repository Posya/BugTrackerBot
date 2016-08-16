package site.kiselev.usersession.state;

import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Just test state for debugging
 */
public class TestState extends State {
    TestState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {
        List<String> out = new ArrayList<>();
        out.add("```text");
        out.add("root");
        out.add("├ 1");
        out.add("│├ 1.1");
        out.add("│└ 1.2");
        out.add("├ 2");
        out.add("└ 3");
        out.add("```");


        String[][] keyboard = {};
        return new Result(out, keyboard);
    }
}
