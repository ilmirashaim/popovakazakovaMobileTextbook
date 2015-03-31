package lim.one.popovakazakova.util;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.EbookApplication;
import lim.one.popovakazakova.R;
import lim.one.popovakazakova.section.ISection;
import lim.one.popovakazakova.section.SectionHelper;

public class SectionFragment extends ListFragment {
    public List<ISection> getSections() {
        return sections;
    }

    public void setSections(List<ISection> sections) {
        this.sections = sections;
    }

    List<ISection> sections;
    public static SectionFragment newInstance() {
        SectionFragment f = new SectionFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_section_list, container, false);
        EbookApplication application = ((EbookApplication) getActivity().getApplication());
        SectionHelper sectionHelper = application.getSectionHelper();
        setSections(sectionHelper.getAllSections());


        setListAdapter(new SectionAdapter(
                getActivity(), R.layout.fragment_section_list_item
        ));


        return rootView;
    }

    private class SectionAdapter extends ArrayAdapter<ISection> {
        private Context context;
        private int layoutResourceId;

        public SectionAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getSections());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        @Override
        public int getCount() {
            return getSections().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
            }
            TextView text = (TextView) row.findViewById(R.id.text);
            ISection iSection = getSections().get(position);
            String name = iSection.getClass().getSimpleName();
            int i = context.getResources().getIdentifier(name, "string", context.getPackageName());
            String str = context.getResources().getString(i);
            text.setText(str);

            return row;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}

