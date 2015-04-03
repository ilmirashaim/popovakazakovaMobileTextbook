package lim.one.popovakazakova.util;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import lim.one.popovakazakova.EbookApplication;
import lim.one.popovakazakova.R;
import lim.one.popovakazakova.section.ISection;
import lim.one.popovakazakova.section.SectionHelper;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class SectionFragment extends SimpleListFragment<ISection> {

    public static SectionFragment newInstance() {
        SectionFragment f = new SectionFragment();
        f.setListViewId(R.layout.fragment_section_list);
        f.setListElementViewId(R.layout.fragment_section_list_item);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EbookApplication application = ((EbookApplication) getActivity().getApplication());
        SectionHelper sectionHelper = application.getSectionHelper();
        setElements(sectionHelper.getAllSections());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView text = (TextView) row.findViewById(R.id.text);
        ISection iSection = getElements().get(position);
        String name = iSection.getClass().getSimpleName();
        int i = getResources().getIdentifier(name, "string", getActivity().getPackageName());
        String str = getResources().getString(i);
        text.setText(str);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ISection section = getElements().get(position);
        EbookApplication application = ((EbookApplication) getActivity().getApplication());
        Class activityClass = application.getSectionHelper().getActivityClass(section);
        Intent intent = new Intent(getActivity(), activityClass);
        Bundle b = new Bundle();
        b.putBoolean("has_no_lesson", true);
        intent.putExtras(b);
        startActivityForResult(intent, 1);
    }

}

