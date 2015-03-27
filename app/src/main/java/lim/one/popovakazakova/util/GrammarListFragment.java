package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Grammar;
import lim.one.popovakazakova.domain.ReadingRule;

public class GrammarListFragment extends ListFragment {
    private List<Grammar> grammars;

    public static GrammarListFragment newInstance(List<Grammar> grammars) {
        GrammarListFragment f = new GrammarListFragment();
        f.setGrammars(grammars);
        return f;
    }

    public List<Grammar> getGrammars() {
        return grammars;
    }

    public void setGrammars(List<Grammar> grammars) {
        this.grammars = grammars;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_grammar_list, container, false);

        setListAdapter(new GrammarAdapter(
                getActivity(), R.layout.fragment_grammar_list_item
        ));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    private class GrammarAdapter extends ArrayAdapter<Grammar> {
        private Context context;
        private int layoutResourceId;

        public GrammarAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getGrammars());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        @Override
        public int getCount() {
            return getGrammars().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                TextView text = (TextView) row.findViewById(R.id.title);

                Grammar grammar = getGrammars().get(position);

                text.setText(Html.fromHtml(grammar.getTextHtml()));
            }

            return row;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}
