package lim.one.popovakazakova.domain;

public class DialogCue {
    private Long id;
    private Long dialogId;
    private String text;
    private String characterName;
    private Integer position;

    private static final String filenameTemplate = "dialogs/dialog%d_%d.mp3";

    public DialogCue() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getFilename() {
        return String.format(filenameTemplate, dialogId, id);
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
