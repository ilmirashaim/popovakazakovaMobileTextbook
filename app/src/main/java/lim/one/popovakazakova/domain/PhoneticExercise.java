package lim.one.popovakazakova.domain;

public class PhoneticExercise {
    private Long id;
    private String filename;
    private Long soundId;
    private String transcription;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
