package lim.one.popovakazakova;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.helper.DialogHelper;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;
import lim.one.popovakazakova.domain.helper.PhraseHelper;
import lim.one.popovakazakova.domain.helper.PhraseWordHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.domain.helper.SoundUsageHelper;
import lim.one.popovakazakova.section.DialogSection;
import lim.one.popovakazakova.section.ISection;
import lim.one.popovakazakova.section.PhoneticExerciseSection;
import lim.one.popovakazakova.section.PhraseSection;
import lim.one.popovakazakova.section.PhraseWordSection;
import lim.one.popovakazakova.section.SectionHelper;
import lim.one.popovakazakova.section.SoundSection;
import lim.one.popovakazakova.util.LessonListAdapter;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        configure();

        EbookApplication application = ((EbookApplication) getApplication());

        List<Lesson> lessons = application.getHelper(LessonHelper.class).getAll();

        List<Pair<Lesson, List<ISection>>> groups = new ArrayList<>();
        for (Lesson l : lessons) {
            List<ISection> sections = application.getSectionHelper().getAllSections(l);
            groups.add(new Pair<>(l, sections));
        }
        LessonListAdapter adapter = new LessonListAdapter(getApplicationContext(), groups);

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.exListView);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new OnLessonSectionClickListener(groups));

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
        application.registerHelper(lessonHelper);
        application.registerHelper(soundHelper);
        application.registerHelper(soundUsageHelper);
        application.registerHelper(phoneticExerciseHelper);
        application.registerHelper(phraseWordHelper);
        application.registerHelper(dialogHelper);
        application.registerHelper(phraseHelper);

        SoundSection soundSection = new SoundSection(soundHelper);
        PhoneticExerciseSection phoneticExerciseSection = new PhoneticExerciseSection(phoneticExerciseHelper);
        PhraseWordSection phraseWordSection = new PhraseWordSection(phraseWordHelper);
        DialogSection dialogSection = new DialogSection(dialogHelper);
        PhraseSection phraseSection = new PhraseSection(phraseHelper);

        SectionHelper sectionHelper = new SectionHelper();
        sectionHelper.registerSection(soundSection, SoundActivity.class);
        sectionHelper.registerSection(phoneticExerciseSection, PhoneticExerciseActivity.class);
        sectionHelper.registerSection(phraseWordSection, PhraseWordActivity.class);
        sectionHelper.registerSection(dialogSection, DialogActivity.class);
        sectionHelper.registerSection(phraseSection, PhraseActivity.class);

        application.setSectionHelper(sectionHelper);
    }

    private class OnLessonSectionClickListener implements ExpandableListView.OnChildClickListener {
        private List<Pair<Lesson, List<ISection>>> groups;

        private OnLessonSectionClickListener(List<Pair<Lesson, List<ISection>>> groups) {
            this.groups = groups;
        }

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Lesson lesson = groups.get(groupPosition).first;
            ISection section = groups.get(groupPosition).second.get(childPosition);
            EbookApplication application = ((EbookApplication) getApplication());
            Class activityClass = application.getSectionHelper().getActivityClass(section);
            Intent intent = new Intent(getOuter(), activityClass);
            Bundle b = new Bundle();
            b.putLong("lesson_id", lesson.getId());
            intent.putExtras(b);
            startActivityForResult(intent, 1);

            return false;
        }
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



