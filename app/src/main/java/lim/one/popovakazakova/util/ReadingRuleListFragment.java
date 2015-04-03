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
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class ReadingRuleListFragment extends SimpleListFragment<ReadingRule> {
    public static ReadingRuleListFragment newInstance(List<ReadingRule> readingRules) {
        ReadingRuleListFragment f = new ReadingRuleListFragment();
        f.setElements(readingRules);
        f.setListViewId(R.layout.fragment_reading_rule_list);
        f.setListElementViewId(R.layout.fragment_reading_rule_list_item);
        return f;
    }

    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView text = (TextView) row.findViewById(R.id.text);
        ReadingRule readingRule = getElements().get(position);
        text.setText(Html.fromHtml(readingRule.getTextHtml()));
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
