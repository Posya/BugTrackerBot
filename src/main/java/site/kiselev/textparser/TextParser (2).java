package site.kiselev.textparser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Parse input to task an date
 */
public class TextParser {

    private Date date;
    private String subj = "";
    private List<Sentence> sentences;

    public TextParser(String input) {
        sentences = new ArrayList<>();
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
