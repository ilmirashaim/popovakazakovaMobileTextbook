package lim.one.popovakazakova;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhoneticExercise;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.domain.SoundUsage;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.domain.helper.SoundUsageHelper;
import lim.one.popovakazakova.util.PhoneticExerciseAdapter;
import lim.one.popovakazakova.util.SoundInfo;
import lim.one.popovakazakova.util.ZoomOutPageTransformer;

public class PhoneticExerciseActivity extends SecondaryActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_phonetic_exercise);
        super.onCreate(savedInstanceState);

        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        SoundHelper soundHelper = application.getHelper(SoundHelper.class);
        PhoneticExerciseHelper phoneticExerciseHelper = application.getHelper(PhoneticExerciseHelper.class);
        SoundUsageHelper soundUsageHelper = application.getHelper(SoundUsageHelper.class);


        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);
        List<Sound> sounds = soundHelper.getAllSounds(lesson);
        List<SoundInfo> soundInfos = new ArrayList<>();
        for (Sound sound : sounds) {
            List<SoundUsage> soundUsages = soundUsageHelper.getSoundUsages(sound);
            List<PhoneticExercise> exercises = phoneticExerciseHelper.getPhoneticExercises(sound);
            if (soundUsages.size() > 0 || exercises.size() > 0) {
                soundInfos.add(new SoundInfo(
                        sound,
                        soundUsages,
                        exercises

                ));
            }
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PhoneticExerciseAdapter(getSupportFragmentManager(),
                soundInfos);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            setResult(1);
            finish();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

}



