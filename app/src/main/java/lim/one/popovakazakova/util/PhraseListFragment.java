package lim.one.popovakazakova.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class PhraseListFragment extends SimpleListFragment<Phrase> {

    public static PhraseListFragment newInstance(List<Phrase> phrases) {
        PhraseListFragment f = new PhraseListFragment();
        f.setElements(phrases);
        f.setListViewId(R.layout.fragment_phrase_list);
        f.setListElementViewId(R.layout.fragment_phrase_list_item);
        return f;
    }


    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView text = (TextView) row.findViewById(R.id.text);
        Phrase phrase = getElements().get(position);
        text.setText(phrase.getText());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}
