package lim.one.popovakazakova.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.PhraseWord;

public class PhraseWordTrainFragment extends Fragment {
    private List<PhraseWord> words;
    private List<PhraseWord> wordsForTrain;
    private SecureRandom random = new SecureRandom();
    private OnTrainFinishListener onTrainFinishListener;

    public interface OnTrainFinishListener {
        public void onTrainFinished();
    }

    public List<PhraseWord> getWordsForTrain() {
        return wordsForTrain;
    }

    public void setWordsForTrain(List<PhraseWord> wordsForTrain) {
        this.wordsForTrain = new ArrayList<>();
        this.wordsForTrain.addAll(wordsForTrain);
    }

    public static PhraseWordTrainFragment newInstance(List<PhraseWord> words) {
        PhraseWordTrainFragment f = new PhraseWordTrainFragment();
        f.setPhraseWords(words);
        f.setWordsForTrain(words);
        return f;
    }

    public List<PhraseWord> getPhraseWords() {
        return words;
    }

    public void setPhraseWords(List<PhraseWord> words) {
        this.words = words;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.onTrainFinishListener = (OnTrainFinishListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_phrase_word_train, container, false);

        showNextWord(rootView);

        Button showButton = (Button) rootView.findViewById(R.id.show_button);
        showButton.setOnClickListener(new ShowButtonClickListener());

        Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new NextButtonClickListener());

        Button finishButton = (Button) rootView.findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new FinishButtonClickListener());

        return rootView;
    }

    public class ShowButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View parent = (View) v.getParent();
            if (parent != null) {
                View translationPart = parent.findViewById(R.id.translation_part);
                translationPart.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
                Button nextButton = (Button) parent.findViewById(R.id.next_button);
                Button finishButton = (Button) parent.findViewById(R.id.finish_button);
                if (wordsForTrain.size() == 1) {
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                } else {
                    nextButton.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    public class NextButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View parent = (View) v.getParent();
            if (parent != null) {
                showNextWord(parent);
            }
        }
    }

    public class FinishButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View parent = (View) v.getParent();
            if (parent != null) {
                onTrainFinishListener.onTrainFinished();
            }
        }
    }

    private void showNextWord(View rootView) {

        PhraseWord phraseWord = getNext();
        if (phraseWord == null) {
            onTrainFinishListener.onTrainFinished();
            return;
        }
        TextView word = (TextView) rootView.findViewById(R.id.word);
        word.setText(phraseWord.getWord());

        TextView translation = (TextView) rootView.findViewById(R.id.translation);
        fillOrHideTextView(translation, phraseWord.getTranslation());

        TextView partOfSpeech = (TextView) rootView.findViewById(R.id.part_of_speech);
        fillOrHideTextView(partOfSpeech, phraseWord.getPartOfSpeech());

        View translationPart = rootView.findViewById(R.id.translation_part);
        translationPart.setVisibility(View.INVISIBLE);

        Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        nextButton.setVisibility(View.GONE);

        Button showButton = (Button) rootView.findViewById(R.id.show_button);
        showButton.setVisibility(View.VISIBLE);
    }

    private PhraseWord getNext() {
        if (wordsForTrain.size() == 0) {
            return null;
        }
        Integer index = random.nextInt(wordsForTrain.size());
        PhraseWord word = wordsForTrain.get(index);
        wordsForTrain.remove(index.intValue());
        return word;
    }

    private void fillOrHideTextView(TextView view, String text) {
        if (text == null || text.isEmpty()) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

}
