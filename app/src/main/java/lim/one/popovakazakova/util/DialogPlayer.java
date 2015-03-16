package lim.one.popovakazakova.util;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.util.view.PlayButton;

public class DialogPlayer implements PlayButton.OnStateChangeListener {

    private MediaPlayer mp;
    private List<DialogCue> forPlay;
    private int pos;
    private boolean isPaused = false;
    private boolean shouldBeRefreshed = true;
    private PlayButton playButton;
    private List<DialogCue> cues;
    private Set<String> computerPart;
    private int next = 0;
    private Handler handler = new Handler();

    public DialogPlayer(PlayButton playButton, List<DialogCue> cues, Set<String> computerPart) {
        this.playButton = playButton;
        this.cues = cues;
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNext();
            }
        });
        this.computerPart = computerPart;
    }

    @Override
    synchronized public void onPlay() {
        if (shouldBeRefreshed) {
            isPaused = false;
            refreshQueue();
            shouldBeRefreshed = false;
        }
        if (isPaused) {
            mp.seekTo(pos);
            mp.start();
            isPaused = false;
            return;
        }

        playNext();
    }

    synchronized public void setComputerPart(Set<String> computerPart) {
        this.computerPart = computerPart;
        this.shouldBeRefreshed = true;
    }

    public void refreshQueue() {
        forPlay = new ArrayList<>();
        for (DialogCue cue : cues) {
            if (computerPart.contains(cue.getCharacterName())) {
                forPlay.add(cue);
            }
        }
        next = 0;
    }

    @Override
    synchronized public void onPause() {
        if (mp.isPlaying()) {
            mp.pause();
            pos = mp.getCurrentPosition();
            isPaused = true;
        }
    }

    synchronized private void playNext() {
        if (next == forPlay.size()) {
            isPaused = false;
            playButton.onFinished();
            next = 0;
            return;
        }
        if (isPaused) {
            return;
        }
        final DialogCue nextCue = forPlay.get(next);
        if (next == 0) {
            play(nextCue);
            return;
        }
        int difference = 0;
        int previous = forPlay.get(next - 1).getPosition();
        for(int i=nextCue.getPosition()-2; i >= previous; i--){
            difference += cues.get(i).getText().length();
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                synchronized (DialogPlayer.this) {
                    play(nextCue);
                }
            }
        }, 100 * difference);
    }

    private void play(DialogCue nextCue) {
        String filename = nextCue.getFilename();
        Log.d("playing", filename);
        try {
            stopPlaying();
            mp.reset();
            AssetFileDescriptor afd = playButton.getContext().getAssets().openFd(filename);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.e("media player exception", "in playing " + filename, e);
        }
        next++;
    }

    synchronized public void stopPlaying() {
        if (mp == null) {
            return;
        }
        if (mp.isPlaying()) {
            mp.stop();
        }
    }
}