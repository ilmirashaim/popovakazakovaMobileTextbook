package lim.one.popovakazakova;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhoneticExercise;
import lim.one.popovakazakova.domain.Section;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;
import lim.one.popovakazakova.domain.helper.SectionHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.util.LessonExpandableListAdapter;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
            if(section.getName().equals("Звуки")) {
                Intent intent = new Intent(getOuter(), SoundActivity.class);
                Bundle b = new Bundle();
                b.putLong("lesson_id", lesson.getId());
                intent.putExtras(b);
                startActivityForResult(intent, 1);
            } else if(section.getName().equals("Фонетические упражнения")){
                Intent intent = new Intent(getOuter(), PhoneticExerciseActivity.class);
                Bundle b = new Bundle();
                b.putLong("lesson_id", lesson.getId());
                intent.putExtras(b);
                startActivityForResult(intent, 1);
            }

            return false;
        }
    }

    @Override
    public void onBackPressed() {}
    public MainActivity getOuter() {
        return MainActivity.this;
    }

}



