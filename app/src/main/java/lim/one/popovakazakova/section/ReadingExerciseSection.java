package lim.one.popovakazakova.section;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.ReadingExercise;
import lim.one.popovakazakova.domain.helper.ReadingExerciseHelper;

public class ReadingExerciseSection implements ISection {

    ReadingExerciseHelper readingExerciseHelper;

    public ReadingExerciseSection(ReadingExerciseHelper readingExerciseHelper) {
        this.readingExerciseHelper = readingExerciseHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        List<ReadingExercise> exercises = readingExerciseHelper.getExercises(lesson);
        return (exercises.size() > 0);
    }
}
