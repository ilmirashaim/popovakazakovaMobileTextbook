package lim.one.popovakazakova.util;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;

public class SoundSlideFragment extends Fragment {

    public static SoundSlideFragment newInstance(Sound sound) {
        SoundSlideFragment f = new SoundSlideFragment();

        // Supply index input as an argument.
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
                R.layout.fragment_screen_slide_page, container, false);
        final TextView content = (TextView) rootView.findViewById(R.id.sound_content);
        content.setText(getArguments().getString("content"));

        final TextView title = (TextView) rootView.findViewById(R.id.sound_title);
        title.setText(getArguments().getString("title"));
//        MediaPlayer mp = new MediaPlayer();
//
//        try {
//            AssetFileDescriptor afd = getActivity().getBaseContext().getAssets().openFd("sounds/da-dat.mp3");
//            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
//            mp.prepare();
//            mp.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return rootView;
    }
}
