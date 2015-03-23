package lim.one.popovakazakova;

import android.os.Bundle;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhraseHelper;
import lim.one.popovakazakova.util.PhraseListFragment;

public class PhraseActivity extends SecondaryActivity {

    private List<Phrase> phrases;
    private PhraseListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_phrase);
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.fragment_container) == null) {
            return;
        }
        if (savedInstanceState != null) {
            return;
        }
        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        PhraseHelper phraseHelper = application.getHelper(PhraseHelper.class);

        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        List<Phrase> phrases = phraseHelper.getPhrases(lesson);
        this.phrases = phrases;
        listFragment = createListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listFragment).commit();
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }


    private PhraseListFragment createListFragment() {
        return PhraseListFragment.newInstance(phrases);
    }

}



