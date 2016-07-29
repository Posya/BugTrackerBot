package site.kiselev.textparser;

/**
 * Class for one sentence
 */
public class Sentence {

    private final String text;
    private SentenceType type;

    public Sentence(String text, SentenceType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public SentenceType getType() {
        return type;
    }
}
