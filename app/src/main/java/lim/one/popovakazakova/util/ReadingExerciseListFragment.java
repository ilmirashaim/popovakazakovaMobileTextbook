package lim.one.popovakazakova.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.ReadingExercise;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class ReadingExerciseListFragment extends SimpleListFragment<ReadingExercise> {

    public static ReadingExerciseListFragment newInstance(List<ReadingExercise> readingExercises) {
        ReadingExerciseListFragment f = new ReadingExerciseListFragment();
        f.setElements(readingExercises);
        f.setListViewId(R.layout.fragment_reading_exercise_list);
        f.setListElementViewId(R.layout.fragment_reading_exercise_list_item);
        return f;
    }


    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView text = (TextView) row.findViewById(R.id.text);
        ReadingExercise readingExercise = getElements().get(position);
        text.setText(readingExercise.getContent());
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
