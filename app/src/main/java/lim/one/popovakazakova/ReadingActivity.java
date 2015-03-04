package lim.one.popovakazakova;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Pair;
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

public class ReadingActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reading);
        SQLiteDatabase db = ((EbookApplication)getApplication()).getDatabase();

        LessonHelper lessonHelper = new LessonHelper(db);
        SoundHelper soundHelper = new SoundHelper(db);
        PhoneticExerciseHelper phoneticExerciseHelper = new PhoneticExerciseHelper(db);
        SectionHelper sectionHelper = new SectionHelper();
        sectionHelper.registerHelper(soundHelper);
        sectionHelper.registerHelper(phoneticExerciseHelper);

        List<Lesson> lessons = lessonHelper.getAll();

        ExpandableListView listView = (ExpandableListView)findViewById(R.id.exListView);

        List<Pair<Lesson, List<Section>>> groups = new ArrayList<>();
        for(Lesson l:lessons){
            List<Section> sections = sectionHelper.getAllSections(l);

            groups.add(new Pair<>(l, sections));
        }
        LessonExpandableListAdapter adapter = new LessonExpandableListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);
        Log.d("starting act", "");
        listView.setOnChildClickListener(new OnLessonSectionClickListener(groups));
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("group clicked", groupPosition + "");
                return false;
            }
        });

    }

    private class OnLessonSectionClickListener implements ExpandableListView.OnChildClickListener {
        private List<Pair<Lesson, List<Section>>> groups;

        private OnLessonSectionClickListener(List<Pair<Lesson, List<Section>>> groups) {
            this.groups = groups;
        }

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Log.d("create an intent", "f");
            Lesson lesson = groups.get(groupPosition).first;
            Section section = groups.get(groupPosition).second.get(childPosition);
            if(section.getName().equals("Звуки")) {
                Log.d("create an intent", "f");

                Intent intent = new Intent(getOuter(), SoundsActivity.class);
                Bundle b = new Bundle();
                b.putLong("lesson_id", lesson.getId());
                intent.putExtras(b);
                Log.d("starting activity", "f");
                startActivity(intent);
                finish();
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {}
    public ReadingActivity getOuter() {
        return ReadingActivity.this;
    }

}



