package lim.one.popovakazakova.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.PhraseWord;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class PhraseWordListFragment extends SimpleListFragment<PhraseWord> {
    private TrainButtonListener trainButtonListener;

    public static PhraseWordListFragment newInstance(List<PhraseWord> words) {
        PhraseWordListFragment f = new PhraseWordListFragment();
        f.setElements(words);
        f.setListViewId(R.layout.fragment_phrase_word_list);
        f.setListElementViewId(R.layout.fragment_phrase_word_list_item);
        return f;
    }

    public interface TrainButtonListener {
        public void onTrain(View v);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        trainButtonListener = (TrainButtonListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        final Button trainButton = (Button) rootView.findViewById(R.id.train_button);
        trainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainButtonListener.onTrain(v);
            }
        });

        return rootView;
    }

    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView word = (TextView) row.findViewById(R.id.word);
        TextView partOfSpeech = (TextView) row.findViewById(R.id.part_of_speech);
        TextView translation = (TextView) row.findViewById(R.id.translation);

        PhraseWord phraseWord = getElements().get(position);

        word.setText(phraseWord.getWord());
        fillOrHideTextView(partOfSpeech, phraseWord.getPartOfSpeech());
        fillOrHideTextView(translation, phraseWord.getTranslation());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    private void fillOrHideTextView(TextView view, String text) {
        if (text == null || text.isEmpty()) {
            view.setVisibility(View.GONE);
        } else {
            view.setText(text);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}
