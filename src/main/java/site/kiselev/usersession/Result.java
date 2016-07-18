package site.kiselev.usersession;

import java.util.Arrays;
import java.util.List;

/**
 * Class for command result keeping
 */
public class Result {
    private List<String> out;
    private String[][] keyboard;

    public Result(List<String> out, String[][] keyboard) {
        this.out = out;
        this.keyboard = keyboard;
    }

    public List<String> getOut() {
        return out;
    }

    public String[][] getKeyboard() {
        return keyboard;
    }

    @Override
    public String toString() {
        return "Result{" +
                "out=" + out +
                ", keyboard=" + Arrays.toString(keyboard) +
                '}';
    }
}
