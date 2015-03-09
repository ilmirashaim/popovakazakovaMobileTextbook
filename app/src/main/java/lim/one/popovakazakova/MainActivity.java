package lim.one.popovakazakova;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Section;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;
import lim.one.popovakazakova.domain.helper.SectionHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.util.LessonExpandableListAdapter;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SQLiteDatabase db = ((EbookApplication) getApplication()).getDatabase();

        LessonHelper lessonHelper = new LessonHelper(db);
        SoundHelper soundHelper = new SoundHelper(db);
        PhoneticExerciseHelper phoneticExerciseHelper = new PhoneticExerciseHelper(db);

        SectionHelper sectionHelper = new SectionHelper();
        sectionHelper.registerHelper(soundHelper);
        sectionHelper.registerHelper(phoneticExerciseHelper);

        List<Lesson> lessons = lessonHelper.getAll();

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.exListView);

        List<Pair<Lesson, List<Section>>> groups = new ArrayList<>();
        for (Lesson l : lessons) {
            List<Section> sections = sectionHelper.getAllSections(l);

            groups.add(new Pair<>(l, sections));
        }
        LessonExpandableListAdapter adapter = new LessonExpandableListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new OnLessonSectionClickListener(groups));

    }

    private class OnLessonSectionClickListener implements ExpandableListView.OnChildClickListener {
        private List<Pair<Lesson, List<Section>>> groups;

        private OnLessonSectionClickListener(List<Pair<Lesson, List<Section>>> groups) {
            this.groups = groups;
        }

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Lesson lesson = groups.get(groupPosition).first;
            Section section = groups.get(groupPosition).second.get(childPosition);
            Intent intent = null;
            if (section.getType().equals(Section.TYPE_SOUNDS)) {
                intent = new Intent(getOuter(), SoundActivity.class);
            } else if (section.getType().equals(Section.TYPE_PHONETIC_EXERCISES)) {
                intent = new Intent(getOuter(), PhoneticExerciseActivity.class);
            }
            if(intent!=null) {
                Bundle b = new Bundle();
                b.putLong("lesson_id", lesson.getId());
                intent.putExtras(b);
                startActivityForResult(intent, 1);
            }

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
        Log.d("click", "hh");
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



