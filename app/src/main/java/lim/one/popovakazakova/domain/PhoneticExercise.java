package lim.one.popovakazakova.domain;

public class PhoneticExercise extends LessonElement{
    private String filename;
    private Long soundId;
    private String transcription;
    private String title;

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSoundId() {
        return soundId;
    }

    public void setSoundId(Long soundId) {
        this.soundId = soundId;
    }
}
