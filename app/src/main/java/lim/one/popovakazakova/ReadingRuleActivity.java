package lim.one.popovakazakova;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhraseHelper;
import lim.one.popovakazakova.domain.helper.ReadingRuleHelper;
import lim.one.popovakazakova.util.PhraseListFragment;
import lim.one.popovakazakova.util.ReadingRuleListFragment;
import lim.one.popovakazakova.util.common.MultiPlayer;
import lim.one.popovakazakova.util.common.PlayButtonMultiPlayerConnector;
import lim.one.popovakazakova.util.view.PlayButton;

public class ReadingRuleActivity extends SecondaryActivity {

    private List<ReadingRule> readingRules;
    private ReadingRuleListFragment listFragment;

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
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        List<ReadingRule> readingRules = readingRuleHelper.getReadingRules(lesson);
        this.readingRules = readingRules;

        listFragment = createListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listFragment).commit();
    }


    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }


    private ReadingRuleListFragment createListFragment() {
        return ReadingRuleListFragment.newInstance(readingRules);
    }

}



