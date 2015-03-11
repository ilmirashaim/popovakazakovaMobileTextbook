package lim.one.popovakazakova.section;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;

public class PhoneticExerciseSection implements ISection {
    PhoneticExerciseHelper phoneticExerciseHelper;

    public PhoneticExerciseSection(PhoneticExerciseHelper phoneticExerciseHelper) {
        this.phoneticExerciseHelper = phoneticExerciseHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        return (phoneticExerciseHelper.hasPhoneticExercises(lesson));
    }
}
