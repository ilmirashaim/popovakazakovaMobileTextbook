package lim.one.popovakazakova.section;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.SoundHelper;

public class SoundSection implements ISection {
    SoundHelper soundHelper;

    public SoundSection(SoundHelper soundHelper) {
        this.soundHelper = soundHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        return (soundHelper.hasSounds(lesson));
    }
}
