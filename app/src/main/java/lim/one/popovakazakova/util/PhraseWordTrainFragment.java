package lim.one.popovakazakova.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.PhraseWord;

public class PhraseWordTrainFragment extends Fragment {
    private List<PhraseWord> words;

    public static PhraseWordTrainFragment newInstance(List<PhraseWord> words) {
        PhraseWordTrainFragment f = new PhraseWordTrainFragment();
        f.setPhraseWords(words);
        return f;
    }

    public List<PhraseWord> getPhraseWords() {
        return words;
    }

    public void setPhraseWords(List<PhraseWord> words) {
        this.words = words;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_phrase_word_train, container, false);
        TextView word = (TextView) rootView.findViewById(R.id.word);
        PhraseWord phraseWord = words.iterator().next();
        word.setText(phraseWord.getWord());

        final TextView translation = (TextView) rootView.findViewById(R.id.translation);
        translation.setText(phraseWord.getTranslation());

        Button showButton = (Button)rootView.findViewById(R.id.show_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translation.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }


}
