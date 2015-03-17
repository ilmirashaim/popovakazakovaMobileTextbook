package lim.one.popovakazakova.util;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.util.view.PlayButton;

public class DialogPlayer implements PlayButton.OnStateChangeListener {

    private MediaPlayer mp;
    private Handler handler = new Handler();
    private Integer pos;
    private boolean isPaused = false;
    private boolean shouldBeRefreshed = true;
    private PlayButton playButton;
    private List<DialogCue> cues;
    private List<DialogCue> forPlay;
    private Set<String> computerPart;
    private int next = 0;

    private OnPlayListener onPlayListener;

    public interface OnPlayListener{
        public void onPlay(DialogCue dialogCue);
    }

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

    public OnPlayListener getOnPlayListener() {
        return onPlayListener;
    }

    public void setOnPlayListener(OnPlayListener onPlayListener) {
        this.onPlayListener = onPlayListener;
    }

    @Override
    synchronized public void onPlay() {
        if (shouldBeRefreshed) {
            isPaused = false;
            refreshQueue();
            shouldBeRefreshed = false;
        }
        if (isPaused && pos != null) {
            mp.seekTo(pos);
            mp.start();
            resetPause();
            return;
        }
        playNext();
    }

    private void resetPause() {
        isPaused = false;
        pos = null;
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
        onPlayListener.onPlay(null);
    }

    @Override
    synchronized public void onPause() {
        if (mp.isPlaying()) {
            mp.pause();
            pos = mp.getCurrentPosition();
        } else {
            pos = null;
        }
        isPaused = true;
    }

    synchronized private void playNext() {
        if (next >= forPlay.size()) {
            resetPause();
            playButton.onFinished();
            next = 0;
            return;
        }
        final int nextPos = next;

        final DialogCue nextCue = forPlay.get(nextPos);

        if (isPaused) {
            resetPause();
            play(nextCue, nextPos);
            return;
        }
        if (next == 0) {
            play(nextCue, nextPos);
            return;
        }
        int difference = 0;
        int previous = forPlay.get(next - 1).getPosition();
        for (int i = nextCue.getPosition() - 2; i >= previous; i--) {
            difference += cues.get(i).getText().length();
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                play(nextCue, nextPos);
            }
        }, (int)(500 * Math.log(difference)));
    }

    synchronized private void play(DialogCue nextCue, int listPos) {
        if (isPaused) {
            return;
        }
        if(listPos != next){
            return;
        }
        String filename = nextCue.getFilename();
        try {
            stopPlaying();
            mp.reset();
            AssetFileDescriptor afd = playButton.getContext().getAssets().openFd(filename);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            if(onPlayListener != null) {
                onPlayListener.onPlay(nextCue);
            }
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