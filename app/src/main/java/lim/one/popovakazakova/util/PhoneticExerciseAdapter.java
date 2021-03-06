package lim.one.popovakazakova.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.PhoneticExercise;

public class PhoneticExerciseAdapter extends BaseAdapter {
    private List<PhoneticExercise> exercises;
    private Context context;
    private LayoutInflater inflater;
    private MediaPlayer mp = new MediaPlayer();
    private ViewGroup active = null;

    private String[] colors = new String[]{"red", "pink", "purple", "indigo", "green", "teal"};

    public PhoneticExerciseAdapter(Context context, List<PhoneticExercise> exercises) {
        this.inflater = LayoutInflater.from(context);
        this.exercises = exercises;
        this.context = context;
        Collections.shuffle(Arrays.asList(this.colors));
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhoneticExercise phoneticExercise = exercises.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_phonetic_exercise, parent, false);
        }
        final ViewGroup titleContainer = (ViewGroup) convertView.findViewById(R.id.ex_title_container);
        titleContainer.setBackgroundColor(getColor(position));

        final TextView content = (TextView) convertView.findViewById(R.id.ex_transcription);
        content.setText(phoneticExercise.getTranscription());

        final TextView title = (TextView) convertView.findViewById(R.id.ex_title);
        title.setText(phoneticExercise.getTitle());

        final ViewGroup playButton = (ViewGroup) convertView.findViewById(R.id.play_button);
        playButton.setOnClickListener(new OnPlayButtonListener(playButton, phoneticExercise.getFilename()));

        return convertView;
    }


    private int getColor(int position) {
        int color = context.getResources().getIdentifier(
                colors[position % colors.length],
                "color",
                context.getPackageName()
        );
        return context.getResources().getColor(color);
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    private class OnPlayButtonListener implements View.OnClickListener {
        private String filename;

        private OnPlayButtonListener(final ViewGroup playButton, String filename) {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    setPlayImage(playButton);
                }
            });
            this.filename = filename;
        }

        @Override
        public void onClick(View v) {
            play((ViewGroup) v, filename);
        }
    }

    private void setStopImage(ViewGroup playButton) {
        setImage(playButton, R.drawable.ic_av_stop);
    }

    private void setPlayImage(ViewGroup playButton) {
        setImage(playButton, R.drawable.ic_av_play_arrow);
    }

    private void setImage(ViewGroup playButton, int id) {
        ((ImageView) playButton.findViewById(R.id.play_button_image)).setImageDrawable(
                context.getResources().getDrawable(id)
        );
    }

    synchronized private void play(ViewGroup playButton, String filename) {
        try {
            if (playButton.equals(active)) {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.reset();
                    setPlayImage(playButton);
                } else {
                    playFile(filename);
                    setStopImage(playButton);
                }
            } else {
                if (active != null) {
                    setPlayImage(active);
                }
                stopPlaying();
                mp.reset();
                playFile(filename);
                setStopImage(playButton);
                active = playButton;
            }
        } catch (Exception e) {
            Log.e("media player exception", "on click", e);
        }
    }

    synchronized private void playFile(String filename) throws IOException {
        AssetFileDescriptor afd = context.getAssets().openFd(filename);
        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mp.prepare();
        mp.start();
    }


    synchronized private void stopPlaying() {
        if (mp.isPlaying()) {
            mp.stop();
        }
    }

}
