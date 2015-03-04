package lim.one.popovakazakova;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.util.SoundSlidePagerAdapter;
import lim.one.popovakazakova.util.ZoomOutPageTransformer;

public class SoundsActivity extends FragmentActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("sounds act", "trying to start");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sounds);
        SQLiteDatabase db = ((EbookApplication)getApplication()).getDatabase();

        LessonHelper lessonHelper = new LessonHelper(db);
        SoundHelper soundHelper = new SoundHelper(db);

        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SoundSlidePagerAdapter(getSupportFragmentManager(), soundHelper.getAllSounds(lesson));
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



