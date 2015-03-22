package lim.one.popovakazakova;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.DialogHelper;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;
import lim.one.popovakazakova.domain.helper.PhraseWordHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.domain.helper.SoundUsageHelper;
import lim.one.popovakazakova.section.DialogSection;
import lim.one.popovakazakova.section.PhoneticExerciseSection;
import lim.one.popovakazakova.section.PhraseWordSection;
import lim.one.popovakazakova.section.SectionHelper;
import lim.one.popovakazakova.section.SoundSection;
import lim.one.popovakazakova.util.LessonListFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        configure();

        EbookApplication application = ((EbookApplication) getApplication());

        List<Lesson> lessons = application.getHelper(LessonHelper.class).getAll();

        LessonListFragment lessonListFragment = LessonListFragment.newInstance(lessons);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lesson_list_container, lessonListFragment).commit();

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
        application.registerHelper(lessonHelper);
        application.registerHelper(soundHelper);
        application.registerHelper(soundUsageHelper);
        application.registerHelper(phoneticExerciseHelper);
        application.registerHelper(phraseWordHelper);
        application.registerHelper(dialogHelper);

        SoundSection soundSection = new SoundSection(soundHelper);
        PhoneticExerciseSection phoneticExerciseSection = new PhoneticExerciseSection(phoneticExerciseHelper);
        PhraseWordSection phraseWordSection = new PhraseWordSection(phraseWordHelper);
        DialogSection dialogSection = new DialogSection(dialogHelper);

        SectionHelper sectionHelper = new SectionHelper();
        sectionHelper.registerSection(soundSection, SoundActivity.class);
        sectionHelper.registerSection(phoneticExerciseSection, PhoneticExerciseActivity.class);
        sectionHelper.registerSection(phraseWordSection, PhraseWordActivity.class);
        sectionHelper.registerSection(dialogSection, DialogActivity.class);

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


}



