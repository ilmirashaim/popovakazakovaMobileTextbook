package lim.one.popovakazakova.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;

public class SoundFragment extends Fragment {

    public static SoundFragment newInstance(Sound sound) {
        SoundFragment f = new SoundFragment();

        Bundle args = new Bundle();
        args.putString("title", sound.getTitle());
        args.putString("content", sound.getContent());
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sound, container, false);
        final TextView content = (TextView) rootView.findViewById(R.id.sound_content);
        content.setText(getArguments().getString("content"));

        final TextView title = (TextView) rootView.findViewById(R.id.sound_title);
        title.setText(getArguments().getString("title"));

        return rootView;
    }
}
