package lim.one.popovakazakova;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.*;
import lim.one.popovakazakova.section.*;
import lim.one.popovakazakova.util.LessonFragment;
import lim.one.popovakazakova.util.SectionFragment;
import lim.one.popovakazakova.util.view.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {
    TabPagerAdapter tabPagerAdapter;
    ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
//        setSupportActionBar(toolbar);

        configure();


        EbookApplication application = ((EbookApplication) getApplication());

        List<Lesson> lessons = application.getHelper(LessonHelper.class).getAll();

        List<Pair<Lesson, List<ISection>>> groups = new ArrayList<>();
        for (Lesson l : lessons) {
            List<ISection> sections = application.getSectionHelper().getAllSections(l);
            groups.add(new Pair<>(l, sections));
        }

        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), groups);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(tabPagerAdapter);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);

    }

    private void configure() {
        EbookApplication application = ((EbookApplication) getApplication());
        SQLiteDatabase db = application.getDatabase();

        LessonHelper lessonHelper = new LessonHelper(db);
        SoundHelper soundHelper = new SoundHelper(db);
        SoundUsageHelper soundUsageHelper = new SoundUsageHelper(db);
        PhoneticExerciseHelper phoneticExerciseHelper = new PhoneticExerciseHelper(db);
        PhraseWordHelper phraseWordHelper = new PhraseWordHelper(db);
        DialogHelper dialogHelper = new DialogHelper(db);
        PhraseHelper phraseHelper = new PhraseHelper(db);
        ReadingRuleHelper readingRuleHelper = new ReadingRuleHelper(db);
        GrammarHelper grammarHelper = new GrammarHelper(db);
        ReadingExerciseHelper readingExerciseHelper = new ReadingExerciseHelper(db);

        application.registerHelper(lessonHelper);
        application.registerHelper(soundHelper);
        application.registerHelper(soundUsageHelper);
        application.registerHelper(phoneticExerciseHelper);
        application.registerHelper(phraseWordHelper);
        application.registerHelper(dialogHelper);
        application.registerHelper(phraseHelper);
        application.registerHelper(readingRuleHelper);
        application.registerHelper(grammarHelper);
        application.registerHelper(readingExerciseHelper);

        SoundSection soundSection = new SoundSection(soundHelper);
        PhoneticExerciseSection phoneticExerciseSection = new PhoneticExerciseSection(phoneticExerciseHelper);
        PhraseWordSection phraseWordSection = new PhraseWordSection(phraseWordHelper);
        DialogSection dialogSection = new DialogSection(dialogHelper);
        PhraseSection phraseSection = new PhraseSection(phraseHelper);
        ReadingRuleSection readingRuleSection = new ReadingRuleSection(readingRuleHelper);
        GrammarSection grammarSection = new GrammarSection(grammarHelper);
        ReadingExerciseSection readingExerciseSection = new ReadingExerciseSection(readingExerciseHelper);

        SectionHelper sectionHelper = new SectionHelper();
        sectionHelper.registerSection(soundSection, SoundActivity.class);
        sectionHelper.registerSection(phoneticExerciseSection, PhoneticExerciseActivity.class);
        sectionHelper.registerSection(phraseWordSection, PhraseWordActivity.class);
        sectionHelper.registerSection(dialogSection, DialogActivity.class);
        sectionHelper.registerSection(phraseSection, PhraseActivity.class);
        sectionHelper.registerSection(readingRuleSection, ReadingRuleActivity.class);
        sectionHelper.registerSection(grammarSection, GrammarActivity.class);
        sectionHelper.registerSection(readingExerciseSection, ReadingExerciseActivity.class);

        application.setSectionHelper(sectionHelper);
    }


    @Override
    public void onBackPressed() {
    }

    public MainActivity getOuter() {
        return MainActivity.this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        List<Pair<Lesson, List<ISection>>> groups;

        public TabPagerAdapter(FragmentManager fm, List<Pair<Lesson, List<ISection>>> groups) {
            super(fm);
            this.groups = groups;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return LessonFragment.newInstance(groups);
            } else {
                return SectionFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(position == 0 ? R.string.tab_lessons : R.string.tab_sections);
        }
    }


}



