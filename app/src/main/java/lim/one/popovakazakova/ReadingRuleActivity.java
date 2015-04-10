package lim.one.popovakazakova;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.ReadingRuleHelper;
import lim.one.popovakazakova.util.ReadingRuleListFragment;

public class ReadingRuleActivity extends SecondaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reading_rule);
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.fragment_container) == null) {
            return;
        }
        if (savedInstanceState != null) {
            return;
        }
        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        ReadingRuleHelper readingRuleHelper = application.getHelper(ReadingRuleHelper.class);

        Bundle b = getIntent().getExtras();


        List<ReadingRule> readingRules;

        Fragment fragment;

        Boolean hasLesson = !b.getBoolean("has_no_lesson", false);
        if (hasLesson) {
            Long lessonId = b.getLong("lesson_id");
            Lesson lesson = lessonHelper.getById(lessonId);
            readingRules = readingRuleHelper.getReadingRules(lesson);
            fragment = ReadingRuleListFragment.newInstance(readingRules);

        } else {
            readingRules = readingRuleHelper.getAllReadingRules();
            Collections.sort(readingRules, new Comparator<ReadingRule>() {
                @Override
                public int compare(ReadingRule lhs, ReadingRule rhs) {
                    return lhs.getTitle().compareTo(rhs.getTitle());
                }
            });
            fragment = ReadingRuleListFragment.newInstance(readingRules);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
    }


    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

}



