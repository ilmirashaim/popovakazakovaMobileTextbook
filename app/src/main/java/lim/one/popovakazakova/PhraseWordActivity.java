package lim.one.popovakazakova;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhraseWord;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhraseWordHelper;
import lim.one.popovakazakova.util.PhraseWordListFragment;
import lim.one.popovakazakova.util.PhraseWordTrainFragment;

public class PhraseWordActivity extends SecondaryActivity
        implements PhraseWordListFragment.TrainButtonListener,
        PhraseWordTrainFragment.OnTrainFinishListener {

    private List<PhraseWord> phraseWords;
    private PhraseWordListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phrase_word);
        if (findViewById(R.id.fragment_container) == null) {
            return;
        }
        if (savedInstanceState != null) {
            return;
        }
        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        PhraseWordHelper phraseWordHelper = application.getHelper(PhraseWordHelper.class);

        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        List<PhraseWord> phraseWords = phraseWordHelper.getPhraseWords(lesson);
        this.phraseWords = phraseWords;
        listFragment = createListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listFragment).commit();
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

    public List<PhraseWord> getPhraseWords() {
        return phraseWords;
    }

    public void setPhraseWords(List<PhraseWord> phraseWords) {
        this.phraseWords = phraseWords;
    }

    @Override
    public void onTrain(View v) {
        PhraseWordTrainFragment trainFragment = PhraseWordTrainFragment.newInstance(getPhraseWords());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, trainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private PhraseWordListFragment createListFragment() {
        return PhraseWordListFragment.newInstance(phraseWords);
    }

    @Override
    public void onTrainFinished() {

        if (listFragment == null) {
            listFragment = createListFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listFragment);
        transaction.commit();
    }
}



