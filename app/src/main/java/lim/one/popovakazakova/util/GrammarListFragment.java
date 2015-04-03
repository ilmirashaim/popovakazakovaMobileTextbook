package lim.one.popovakazakova.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Grammar;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class GrammarListFragment extends SimpleListFragment<Grammar> {
    public static GrammarListFragment newInstance(List<Grammar> grammars) {
        GrammarListFragment f = new GrammarListFragment();
        f.setElements(grammars);
        f.setListViewId(R.layout.fragment_grammar_list);
        f.setListElementViewId(R.layout.fragment_grammar_list_item);
        return f;
    }

    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView text = (TextView) row.findViewById(R.id.text);
        TextView title = (TextView) row.findViewById(R.id.title);

        Grammar grammar = getElements().get(position);

        text.setText(Html.fromHtml(grammar.getTextHtml()));
        title.setText(grammar.getTitle());
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
