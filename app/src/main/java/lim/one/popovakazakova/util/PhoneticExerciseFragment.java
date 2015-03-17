package lim.one.popovakazakova.util;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.PhoneticExercise;

public class PhoneticExerciseFragment extends Fragment {
    private MediaPlayer mp = new MediaPlayer();

    public static PhoneticExerciseFragment newInstance(PhoneticExercise exercise) {
        PhoneticExerciseFragment f = new PhoneticExerciseFragment();

        Bundle args = new Bundle();
        args.putString("title", exercise.getTitle());
        args.putString("transcription", exercise.getTranscription());
        args.putString("filename", exercise.getFilename());
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_phonetic_exercise, container, false);
        final TextView content = (TextView) rootView.findViewById(R.id.ex_transcription);
        content.setText(getArguments().getString("transcription"));

        final TextView title = (TextView) rootView.findViewById(R.id.ex_title);
        title.setText(getArguments().getString("title"));

        final Button playButton = (Button) rootView.findViewById(R.id.play_button);
        playButton.setOnClickListener(new OnPlayButtonListener());

        return rootView;
    }


    private class OnPlayButtonListener implements View.OnClickListener {

        private OnPlayButtonListener() {
            String filename = getArguments().getString("filename");

            try {
                AssetFileDescriptor afd = getActivity().getBaseContext().getAssets().openFd(filename);
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            } catch (IOException e) {
                Log.e("media player exception", "in constructor", e);
            }
        }


        @Override
        public void onClick(View v) {
            try {

                if (mp.isPlaying()) {
                    mp.stop();
                    ((Button)v).setText(R.string.play_sign);
                } else {
                    mp.prepare();
                    mp.start();
                    ((Button)v).setText("||");
                }
            } catch (Exception e) {
                Log.e("media player exception", "on click", e);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlaying();
    }


    private void stopPlaying() {
        if (mp == null) {
            return;
        }
        if (mp.isPlaying()) {
            mp.stop();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
           stopPlaying();
        }
    }
}
