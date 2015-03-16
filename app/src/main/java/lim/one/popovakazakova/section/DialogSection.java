package lim.one.popovakazakova.section;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.DialogHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;

public class DialogSection implements ISection {
    DialogHelper dialogHelper;

    public DialogSection(DialogHelper dialogHelper) {
        this.dialogHelper = dialogHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        return (dialogHelper.hasDialogs(lesson));
    }
}
