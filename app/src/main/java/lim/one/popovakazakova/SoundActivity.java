package lim.one.popovakazakova;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.util.SoundListFragment;
import lim.one.popovakazakova.util.SoundTitlesListFragment;

public class SoundActivity extends SecondaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sound);
        super.onCreate(savedInstanceState);

        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        SoundHelper soundHelper = application.getHelper(SoundHelper.class);

        Bundle b = getIntent().getExtras();
        Boolean hasLesson = !b.getBoolean("has_no_lesson", false);
        List<Sound> soundsList;
        Fragment f;
        if (hasLesson) {
            Long lessonId = b.getLong("lesson_id");
            Lesson lesson = lessonHelper.getById(lessonId);
            soundsList = soundHelper.getAllSounds(lesson);
            f = SoundListFragment.newInstance(soundsList);
        } else {
            soundsList = soundHelper.getAllSounds();
            Collections.sort(soundsList, new Comparator<Sound>() {
                @Override
                public int compare(Sound lhs, Sound rhs) {
                    return lhs.getTitle().charAt(0) - rhs.getTitle().charAt(0);
                }
            });
            f = SoundTitlesListFragment.newInstance(soundsList);
        }


        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, f).commit();

    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

}



