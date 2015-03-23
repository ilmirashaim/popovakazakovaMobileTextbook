package lim.one.popovakazakova.domain;

public class Phrase extends LessonElement {
    private String text;

    private static final String filenameTemplate = "phrases/%d_%d.mp3";

    public Phrase() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilename() {
        return String.format(filenameTemplate, getLessonId(), getId());
    }
}
