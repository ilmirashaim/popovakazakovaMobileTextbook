package lim.one.popovakazakova;

import android.os.Bundle;

import java.util.List;

import lim.one.popovakazakova.domain.Grammar;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.GrammarHelper;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.util.GrammarListFragment;

public class GrammarActivity extends SecondaryActivity {

    private List<Grammar> grammars;
    private GrammarListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_grammar);
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.fragment_container) == null) {
            return;
        }
        if (savedInstanceState != null) {
            return;
        }
        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        GrammarHelper grammarHelper = application.getHelper(GrammarHelper.class);

        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        List<Grammar> grammars = grammarHelper.getGrammars(lesson);
        this.grammars = grammars;

        listFragment = createListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listFragment).commit();
    }


    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }


    private GrammarListFragment createListFragment() {
        return GrammarListFragment.newInstance(grammars);
    }

}



