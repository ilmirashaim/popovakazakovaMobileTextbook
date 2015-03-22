package lim.one.popovakazakova.util;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Lesson;

public class LessonListAdapter extends ArrayAdapter<Lesson> {

    private Map<Long, Pair<ListAdapter, AdapterView.OnItemClickListener>> lessonSectionHelpers;
    private List<Lesson> lessons;
    private Context context;

    private int expanded = -1;

    public LessonListAdapter(Context context,
                             List<Lesson> lessons,
                             Map<Long, Pair<ListAdapter, AdapterView.OnItemClickListener>> lessonSectionHelpers) {
        super(context, R.layout.lesson_view, lessons);
        this.context = context;
        this.lessonSectionHelpers = lessonSectionHelpers;
        this.lessons = lessons;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lesson_view, null);
        }

        Lesson lesson = lessons.get(position);
        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText(lesson.getTitle());

        ListView listView = (ListView) convertView.findViewById(R.id.section_list);
        if (listView.getAdapter() == null) {
            ListAdapter adapter = lessonSectionHelpers.get(lesson.getId()).first;
            listView.setAdapter(adapter);
        }
        if (listView.getOnItemClickListener() == null) {
            listView.setOnItemClickListener(lessonSectionHelpers.get(lesson.getId()).second);
        }


        if (position == expanded) {
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
        }

        return convertView;
    }


    public int getExpanded() {
        return expanded;
    }

    public void setExpanded(int expanded) {
        if(expanded == this.expanded){
            this.expanded = -1;
        }else {
            this.expanded = expanded;
        }
    }


}

