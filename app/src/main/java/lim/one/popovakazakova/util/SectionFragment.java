package lim.one.popovakazakova.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lim.one.popovakazakova.R;

public class SectionFragment extends ListFragment {
    public static SectionFragment newInstance() {
        SectionFragment f = new SectionFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sound, container, false);

        return rootView;
    }
}

