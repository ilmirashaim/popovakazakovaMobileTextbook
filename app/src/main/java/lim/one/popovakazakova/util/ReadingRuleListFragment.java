package lim.one.popovakazakova.util;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class ReadingRuleListFragment extends SimpleListFragment<ReadingRule> {
    private String[] colors = new String[]{"red", "pink", "purple", "indigo", "green", "teal"};

    public void shuffle() {
        Arrays.sort(colors);
    }

    public static ReadingRuleListFragment newInstance(List<ReadingRule> readingRules) {
        ReadingRuleListFragment f = new ReadingRuleListFragment();
        f.setElements(readingRules);
        f.setListViewId(R.layout.fragment_reading_rule_list);
        f.setListElementViewId(R.layout.fragment_reading_rule_list_item);
        f.shuffle();
        return f;
    }


    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView text = (TextView) row.findViewById(R.id.text);
        ReadingRule readingRule = getElements().get(position);
        text.setText(Html.fromHtml(readingRule.getTextHtml()));

        TextView ruleTitle = (TextView) row.findViewById(R.id.rule_title);
        ruleTitle.setText(readingRule.getTitle());
        ruleTitle.getBackground().setColorFilter(getResources().getColor(
                getResources().getIdentifier(
                        colors[position % colors.length], "color", getActivity().getPackageName()
                )
        ), PorterDuff.Mode.SRC);
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
