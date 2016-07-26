package site.kiselev.textparser;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Date;
import java.util.List;

/**
 * Parse input to task an date
 */
public class TextParser {

    private Date date;
    private String subj = "";

    public TextParser(String input) {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(input);
        if (groups.size() > 0) {
            DateGroup group = groups.get(0);
            subj = group.getFullText().substring(0, group.getAbsolutePosition());
            if (group.getDates().size() > 0) {
                date = group.getDates().get(0);
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public String getSubj() {
        return subj;
    }

    @Override
    public String toString() {
        return "TextParser{" +
                "date=" + date +
                ", subj='" + subj + '\'' +
                '}';
    }
}
