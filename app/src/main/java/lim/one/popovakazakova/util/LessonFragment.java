package lim.one.popovakazakova.util;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import lim.one.popovakazakova.EbookApplication;
import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.section.ISection;

public class LessonFragment extends Fragment {
    public List<Pair<Lesson, List<ISection>>> getGroups() {
        return groups;
    }

    public void setGroups(List<Pair<Lesson, List<ISection>>> groups) {
        this.groups = groups;
    }

    private List<Pair<Lesson, List<ISection>>> groups;

    public static LessonFragment newInstance(List<Pair<Lesson, List<ISection>>> groups) {
        LessonFragment f = new LessonFragment();
        f.setGroups(groups);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_lesson_list, container, false);

        LessonListAdapter adapter = new LessonListAdapter(getActivity().getApplicationContext(), groups);
        ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.exListView);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new OnLessonSectionClickListener(groups));

        return rootView;
    }

    private class OnLessonSectionClickListener implements ExpandableListView.OnChildClickListener {
        private List<Pair<Lesson, List<ISection>>> groups;

        private OnLessonSectionClickListener(List<Pair<Lesson, List<ISection>>> groups) {
            this.groups = groups;
        }

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Lesson lesson = groups.get(groupPosition).first;
            ISection section = groups.get(groupPosition).second.get(childPosition);
            EbookApplication application = ((EbookApplication) getActivity().getApplication());
            Class activityClass = application.getSectionHelper().getActivityClass(section);
            Intent intent = new Intent(getActivity(), activityClass);
            Bundle b = new Bundle();
            b.putLong("lesson_id", lesson.getId());
            intent.putExtras(b);
            startActivityForResult(intent, 1);

            return false;
        }
    }


}

