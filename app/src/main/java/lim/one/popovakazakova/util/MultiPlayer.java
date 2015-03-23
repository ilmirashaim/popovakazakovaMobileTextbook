package lim.one.popovakazakova.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 Player that wraps standart MediaPlayer
 Can play several tracks (MultiPlayer.Track)/
 Can pause and resume.

 --gap--
 It also provides a gap between tracks.
 The gap is retrieved from the  TrackGapProvider.

 --resume--
 If paused when the track plays, the resuming will lead to normal resume.
 If paused on the gap between tracks the resuming will play next track without(!) any gap.

 */
public class MultiPlayer {

    private Context context;
    private MediaPlayer mp;
    private Handler handler = new Handler();
    private Integer pos;
    private boolean isPaused = false;
    private List<Track> forPlay;
    private List<Track> forRefresh;
    private boolean isFinished = false;
    private int next = 0;

    private OnPlayListener onPlayListener;
    private TrackGapProvider trackGapProvider;
    private OnFinishedListener onFinishedListener;

    static final public class Track {
        Long id;
        String filename;

        public Track(Long id, String filename) {
            this.id = id;
            this.filename = filename;
        }
    }

    public interface TrackGapProvider {
        public int getGap(Track previous, Track current);
    }

    public interface OnPlayListener {
        public void onPlay(Track track);
    }

    public interface OnFinishedListener {
        public void onFinished();
    }

    public MultiPlayer(Context context) {
        this.context = context;
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNext();
            }
        });
    }

    public OnPlayListener getOnPlayListener() {
        return onPlayListener;
    }

    public void setOnPlayListener(OnPlayListener onPlayListener) {
        this.onPlayListener = onPlayListener;
    }

    public TrackGapProvider getTrackGapProvider() {
        return trackGapProvider;
    }

    public void setTrackGapProvider(TrackGapProvider trackGapProvider) {
        this.trackGapProvider = trackGapProvider;
    }

    public OnFinishedListener getOnFinishedListener() {
        return onFinishedListener;
    }

    public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    synchronized public void play() {
        if (forRefresh != null) {
            isPaused = false;
            refreshQueue();
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

    synchronized public void setForRefresh(List<Track> forRefresh) {
        this.forRefresh = forRefresh;
    }

    public void refreshQueue() {
        if (forRefresh == null) {
            return;
        }
        forPlay = forRefresh;

        next = 0;
        onPlayListener.onPlay(null);
        forRefresh = null;
    }

    synchronized public void pause() {
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
            if (onFinishedListener != null) {
                onFinishedListener.onFinished();
            }
            next = 0;
            return;
        }
        final int nextPos = next;

        final Track nextTrack = forPlay.get(nextPos);

        if (isPaused) {
            resetPause();
            play(nextTrack, nextPos);
            return;
        }
        if (next == 0) {
            play(nextTrack, nextPos);
            return;
        }
        int gap = 0;
        if (trackGapProvider != null) {
            gap = trackGapProvider.getGap(forPlay.get(nextPos - 1), nextTrack);
        }
        Log.d("gap is", gap+"");

        handler.postDelayed(new Runnable() {
            public void run() {
                play(nextTrack, nextPos);
            }
        }, gap);
    }

    synchronized private void play(Track nextTrack, int listPos) {
        if (isPaused) {
            return;
        }
        if (isFinished) {
            return;
        }
        if (listPos != next) {
            return;
        }
        try {
            stopPlaying();
            mp.reset();
            AssetFileDescriptor afd = context.getAssets().openFd(nextTrack.filename);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            if (onPlayListener != null) {
                onPlayListener.onPlay(nextTrack);
            }
            mp.start();
        } catch (IOException e) {
            Log.e("media player exception", "in playing " + nextTrack, e);
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

    synchronized public void onFinished() {
        stopPlaying();
        isFinished = true;
        if (onFinishedListener != null) {
            onFinishedListener.onFinished();
        }
    }
}