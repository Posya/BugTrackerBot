package site.kiselev.usersession.state;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by posya on 7/25/16.
 */
public class ReminderStateTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void nattyTest() throws Exception {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse("write a book next monday morning");
        for(DateGroup group : groups) {
            String prefix = group.getFullText().substring(0, group.getAbsolutePosition());
            List dates = group.getDates();
            int line = group.getLine();
            int column = group.getPosition();
            String matchingValue = group.getText();
            String syntaxTree = group.getSyntaxTree().toStringTree();
            Map<String, List<ParseLocation>> parseLocations = group.getParseLocations();
            boolean isRecurreing = group.isRecurring();
            Date recursUntil = group.getRecursUntil();
        }

    }

}