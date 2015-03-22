package lim.one.popovakazakova.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.section.ISection;

public class LessonSectionListAdapter extends ArrayAdapter<ISection> {

    private List<ISection> sections;
    private Context context;

    public LessonSectionListAdapter(Context context, List<ISection> sections) {
        super(context, R.layout.section_view, sections);
        this.context = context;
        this.sections = sections;
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.section_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        ISection section = sections.get(position);
        String name = section.getClass().getSimpleName();
        int i = context.getResources().getIdentifier(name, "string", context.getPackageName());
        String str = context.getResources().getString(i);
        textChild.setText(str);

        return convertView;
    }


}

