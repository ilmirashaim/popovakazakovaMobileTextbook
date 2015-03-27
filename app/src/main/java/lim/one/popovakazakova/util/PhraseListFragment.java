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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Phrase;

public class PhraseListFragment extends ListFragment {
    private List<Phrase> phrases;

    public static PhraseListFragment newInstance(List<Phrase> phrases) {
        PhraseListFragment f = new PhraseListFragment();
        f.setPhrases(phrases);
        return f;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_phrase_list, container, false);

        setListAdapter(new PhraseAdapter(
                getActivity(), R.layout.fragment_phrase_list_item
        ));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    private class PhraseAdapter extends ArrayAdapter<Phrase> {
        private Context context;
        private int layoutResourceId;

        public PhraseAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getPhrases());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        @Override
        public int getCount() {
            return getPhrases().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
            }
            TextView text = (TextView) row.findViewById(R.id.text);
            Phrase phrase = getPhrases().get(position);
            text.setText(phrase.getText());

            return row;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}
