package lim.one.popovakazakova;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.ReadingExercise;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhraseHelper;
import lim.one.popovakazakova.domain.helper.ReadingExerciseHelper;
import lim.one.popovakazakova.util.PhraseListFragment;
import lim.one.popovakazakova.util.ReadingExerciseListFragment;
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
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        List<ReadingExercise> readingExercises = readingExerciseHelper.getExercises(lesson);

        ReadingExerciseListFragment listFragment = ReadingExerciseListFragment.newInstance(readingExercises);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listFragment).commit();
    }

}



