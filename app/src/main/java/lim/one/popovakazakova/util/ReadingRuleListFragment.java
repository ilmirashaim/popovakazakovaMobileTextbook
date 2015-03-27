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
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.ReadingRule;

public class ReadingRuleListFragment extends ListFragment {
    private List<ReadingRule> readingRules;

    public static ReadingRuleListFragment newInstance(List<ReadingRule> readingRules) {
        ReadingRuleListFragment f = new ReadingRuleListFragment();
        f.setReadingRules(readingRules);
        return f;
    }

    public List<ReadingRule> getReadingRules() {
        return readingRules;
    }

    public void setReadingRules(List<ReadingRule> readingRules) {
        this.readingRules = readingRules;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_reading_rule_list, container, false);

        setListAdapter(new ReadingRuleAdapter(
                getActivity(), R.layout.fragment_reading_rule_list_item
        ));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    private class ReadingRuleAdapter extends ArrayAdapter<ReadingRule> {
        private Context context;
        private int layoutResourceId;

        public ReadingRuleAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getReadingRules());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        @Override
        public int getCount() {
            return getReadingRules().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                TextView text = (TextView) row.findViewById(R.id.text);

                ReadingRule readingRule = getReadingRules().get(position);

                text.setText(Html.fromHtml(readingRule.getTextHtml()), TextView.BufferType.SPANNABLE);
            }

            return row;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}
