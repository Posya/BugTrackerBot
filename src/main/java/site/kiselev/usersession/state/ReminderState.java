package site.kiselev.usersession.state;

import com.google.common.base.Strings;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;
import site.kiselev.task.Task;
import site.kiselev.usersession.Config;
import site.kiselev.usersession.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static site.kiselev.task.Task.ROOT_ID;

/**
 * State to set or delete reminder
 */
class ReminderState extends State {
    ReminderState(Config config, long id) {
        super(config, id);
    }

    @Override
    Result getResult() {

        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse("the day before next thursday");
        for(DateGroup group : groups) {
            List dates = group.getDates();
            int line = group.getLine();
            int column = group.getPosition();
            String matchingValue = group.getText();
            String syntaxTree = group.getSyntaxTree().toStringTree();
            Map<String, List<ParseLocation>> parseLocations = group.getParseLocations();
            boolean isRecurreing = group.isRecurring();
            Date recursUntil = group.getRecursUntil();
        }
        return null;
    }
}
