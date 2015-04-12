package lim.one.popovakazakova.util;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class ReadingRuleTitlesListFragment extends SimpleListFragment<ReadingRule> {
    private String[] colors = new String[]{"red", "pink", "purple", "indigo", "green", "teal"};
    int visible = -1;

    public void shuffle() {
        Arrays.sort(colors);
    }

    public static ReadingRuleTitlesListFragment newInstance(List<ReadingRule> readingRules) {
        ReadingRuleTitlesListFragment f = new ReadingRuleTitlesListFragment();
        f.setElements(readingRules);
        f.setListViewId(R.layout.fragment_reading_rule_list);
        f.setListElementViewId(R.layout.fragment_reading_rule_titles_list_item);
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
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (visible == position) {
            visible = -1;
            setItemSelected(position, false);
        } else {
            if(visible >=0) {
                setItemSelected(visible, false);
            }
            visible = position;
            setItemSelected(position, true);
            getListView().smoothScrollToPosition(position); //todo: check it
        }
    }

    public void setItemSelected(int position, boolean selected){
        View v = getViewByPosition(position, getListView());
        TextView text = (TextView) v.findViewById(R.id.text);
        RelativeLayout layout = (RelativeLayout) text.getParent();
        if(selected){
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            applySingleLine(text, false);
        }else{
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height = (int) getResources().getDimension(R.dimen.list_with_avatar_height);
            applySingleLine(text, true);
        }
    }

    private void applySingleLine(TextView textView, boolean singleLine) {
        if (singleLine) {
            textView.setMaxLines(1);
            textView.setHorizontallyScrolling(true);
            textView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
            textView.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            textView.setMaxLines(Integer.MAX_VALUE);
            textView.setHorizontallyScrolling(false);
            textView.setTransformationMethod(null);
            textView.setEllipsize(null);
        }
    }

}
