package lim.one.popovakazakova;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.ReadingExercise;
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhraseHelper;
import lim.one.popovakazakova.domain.helper.ReadingExerciseHelper;
import lim.one.popovakazakova.util.PhraseListFragment;
import lim.one.popovakazakova.util.ReadingExerciseListFragment;
import lim.one.popovakazakova.util.ReadingRuleListFragment;
import lim.one.popovakazakova.util.ReadingRuleTitlesListFragment;
import lim.one.popovakazakova.util.common.MultiPlayer;
import lim.one.popovakazakova.util.common.PlayButtonMultiPlayerConnector;
import lim.one.popovakazakova.util.view.PlayButton;

public class ReadingExerciseActivity extends SecondaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reading_exercise);
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.fragment_container) == null) {
            return;
        }
        if (savedInstanceState != null) {
            return;
        }
        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        ReadingExerciseHelper readingExerciseHelper = application.getHelper(ReadingExerciseHelper.class);

        Bundle b = getIntent().getExtras();
        Boolean hasLesson = !b.getBoolean("has_no_lesson", false);
        List<ReadingExercise> readingExercises;
        Fragment fragment;
        if (hasLesson) {
            Long lessonId = b.getLong("lesson_id");
            Lesson lesson = lessonHelper.getById(lessonId);
            readingExercises = readingExerciseHelper.getExercises(lesson);
            fragment = ReadingExerciseListFragment.newInstance(readingExercises);

        } else {
            readingExercises = readingExerciseHelper.getAllExercises();
            fragment = ReadingExerciseListFragment.newInstance(readingExercises);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
    }

}



