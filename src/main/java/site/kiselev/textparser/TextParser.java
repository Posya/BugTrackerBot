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
        for (String w : input.split("\\s")) {
            sentences.add(new Sentence(w, SentenceType.TEXT));
        }
    }

    public boolean toDigital() {
//        for (Sentence s : sentences) {
//            switch (s.getText().toLowerCase()) {
//                case "понедельник":
//
//            }
//        }
        return false;
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
