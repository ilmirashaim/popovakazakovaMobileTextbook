package lim.one.popovakazakova;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhraseHelper;
import lim.one.popovakazakova.util.common.MultiPlayer;
import lim.one.popovakazakova.util.PhraseListFragment;
import lim.one.popovakazakova.util.view.PlayButton;
import lim.one.popovakazakova.util.common.PlayButtonMultiPlayerConnector;

public class PhraseActivity extends SecondaryActivity {

    private List<Phrase> phrases;
    private PhraseListFragment listFragment;
    private MultiPlayer multiPlayer;

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

        PlayButton playButton = (PlayButton)findViewById(R.id.play_button);
        final MultiPlayer multiPlayer = new MultiPlayer(this);
        multiPlayer.setForRefresh(getTracks());
        new PlayButtonMultiPlayerConnector(playButton, multiPlayer);
        this.multiPlayer = multiPlayer;


        listFragment = createListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listFragment).commit();
    }

    private List<MultiPlayer.Track> getTracks(){
        List<MultiPlayer.Track> tracks = new ArrayList<>();
        if(phrases == null){
            return tracks;
        }
        for(Phrase phrase : phrases){
            tracks.add(new MultiPlayer.Track(phrase.getId(), phrase.getFilename()));
        }
        return tracks;
    }

    @Override
    public void onStop(){
        super.onStop();
        if (multiPlayer != null) {
            multiPlayer.onFinished();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (multiPlayer != null) {
            multiPlayer.onFinished();
        }
    }

    @Override
    public void onBackPressed() {
        if (multiPlayer != null) {
            multiPlayer.onFinished();
        }
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



