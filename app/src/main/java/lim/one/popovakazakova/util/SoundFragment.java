package lim.one.popovakazakova.util;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;

public class SoundFragment extends ListFragment {
    List<Sound> sounds;

    public static SoundFragment newInstance(List<Sound> sounds) {
        SoundFragment f = new SoundFragment();

        f.setSounds(sounds);

        return f;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(List<Sound> sounds) {
        this.sounds = sounds;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sound_list, container, false);

        setListAdapter(new SoundAdapter(
                getActivity(), R.layout.fragment_sound, sounds
        ));

        return rootView;
    }

}
