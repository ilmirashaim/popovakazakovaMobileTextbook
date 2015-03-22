package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lim.one.popovakazakova.EbookApplication;
import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.section.ISection;

public class LessonListFragment extends ListFragment {
    List<Lesson> lessons;

    public static LessonListFragment newInstance(List<Lesson> lessons) {
        LessonListFragment f = new LessonListFragment();
        f.setLessons(lessons);
        return f;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_lesson_list, container, false);

        Map<Long, Pair<ListAdapter, AdapterView.OnItemClickListener>> groups = new HashMap<>();
        for (Lesson l : lessons) {
            List<ISection> sections = ((EbookApplication) getActivity().getApplication()).getSectionHelper().getAllSections(l);
            Log.e("sections size", sections.size()+"");
            groups.put(l.getId(), new Pair<>(
                    (ListAdapter) new LessonSectionListAdapter(getActivity(), sections),
                    (AdapterView.OnItemClickListener) new OnLessonSectionClickListener(l.getId(), sections)));
        }
        setListAdapter(new LessonListAdapter(getActivity(), lessons, groups));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(new OnLessonClickListener());
    }

    private class OnLessonClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ((LessonListAdapter) parent.getAdapter()).setExpanded(position);
            ((LessonListAdapter) parent.getAdapter()).notifyDataSetChanged();
        }
    }

    public class OnLessonSectionClickListener implements AdapterView.OnItemClickListener {
        private List<ISection> sections;
        private Long lessonId;

        public OnLessonSectionClickListener(Long lessonId, List<ISection> sections) {
            this.sections = sections;
            this.lessonId = lessonId;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("on item clicked", position+"");
            ISection section = sections.get(position);
            EbookApplication application = ((EbookApplication) getActivity().getApplication());
            Class activityClass = application.getSectionHelper().getActivityClass(section);
            Intent intent = new Intent(getActivity(), activityClass);
            Bundle b = new Bundle();
            b.putLong("lesson_id", lessonId);
            intent.putExtras(b);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }
}

