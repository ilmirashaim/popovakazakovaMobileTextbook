package lim.one.popovakazakova;

import android.os.Bundle;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.util.SoundFragment;

public class SoundActivity extends SecondaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sound);
        super.onCreate(savedInstanceState);

        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        SoundHelper soundHelper = application.getHelper(SoundHelper.class);

        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        SoundFragment listFragment = SoundFragment.newInstance(soundHelper.getAllSounds(lesson));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, listFragment).commit();

    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

}



