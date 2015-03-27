package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.PhraseWord;

public class PhraseWordListFragment extends ListFragment {
    private List<PhraseWord> words;
    private TrainButtonListener trainButtonListener;

    public static PhraseWordListFragment newInstance(List<PhraseWord> words) {
        PhraseWordListFragment f = new PhraseWordListFragment();
        f.setPhraseWords(words);
        return f;
    }

    public List<PhraseWord> getPhraseWords() {
        return words;
    }

    public void setPhraseWords(List<PhraseWord> words) {
        this.words = words;
    }

    public interface TrainButtonListener {
        public void onTrain(View v) ;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        trainButtonListener = (TrainButtonListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_phrase_word_list, container, false);

        final Button trainButton = (Button) rootView.findViewById(R.id.train_button);
        trainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                trainButtonListener.onTrain(v);
            }
        });

        setListAdapter(new PhraseWordsAdapter(
                getActivity(), R.layout.fragment_phrase_word_list_item
        ));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    private class PhraseWordsAdapter extends ArrayAdapter<PhraseWord> {
        private Context context;
        private int layoutResourceId;

        public PhraseWordsAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getPhraseWords());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }
        @Override
        public int getCount() {
            return getPhraseWords().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
            }
            TextView word = (TextView) row.findViewById(R.id.word);
            TextView partOfSpeech = (TextView) row.findViewById(R.id.part_of_speech);
            TextView translation = (TextView) row.findViewById(R.id.translation);

            PhraseWord phraseWord = getPhraseWords().get(position);

            word.setText(phraseWord.getWord());
            fillOrHideTextView(partOfSpeech, phraseWord.getPartOfSpeech());
            fillOrHideTextView(translation, phraseWord.getTranslation());

            return row;
        }

        private void fillOrHideTextView(TextView view, String text) {
            if(text == null || text.isEmpty()){
                view.setVisibility(View.GONE);
            }else{
                view.setText(text);
            }
        }

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}
