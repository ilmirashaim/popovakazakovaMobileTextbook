package lim.one.popovakazakova.domain;

public class ReadingRule extends LessonElement{
    private String textHtml;
    private String title;

    public String getTextHtml() {
        return textHtml;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
