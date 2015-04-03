package lim.one.popovakazakova.util.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


abstract public class SimpleListFragment<T> extends ListFragment {
    protected List<T> elements;
    private int listViewId;
    private int listElementViewId;


    public void setListElementViewId(int listElementViewId) {
        this.listElementViewId = listElementViewId;
    }

    public void setListViewId(int listViewId) {
        this.listViewId = listViewId;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                listViewId, container, false);

        setListAdapter(new ElementAdapter(
                getActivity(), listElementViewId
        ));

        return rootView;
    }

    abstract public void fillRow(int position, View row, ViewGroup parent);


    private class ElementAdapter extends ArrayAdapter<T> {
        private Context context;
        private int layoutResourceId;

        public ElementAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getElements());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        @Override
        public int getCount() {
            return getElements().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
            }
            fillRow(position, row, parent);

            return row;
        }
    }

}
