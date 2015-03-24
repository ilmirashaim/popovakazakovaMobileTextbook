package lim.one.popovakazakova.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.util.common.MultiPlayer;

public class DialogPlayer extends MultiPlayer implements MultiPlayer.TrackGapProvider {

    private List<DialogCue> cues;
    private Set<String> computerPart;

    private OnPlayListener onPlayListener;

    public interface OnPlayListener {
        public void onPlay(DialogCue dialogCue);
    }

    public DialogPlayer(Context context, List<DialogCue> cues, Set<String> computerPart) {
        super(context);

        this.cues = cues;
        this.computerPart = computerPart;

        setForRefresh(getTracks());
        setTrackGapProvider(this);
    }


    public void setOnPlayListener(final OnPlayListener onPlayListener) {
        this.onPlayListener = onPlayListener;
        super.setOnPlayListener(new MultiPlayer.OnPlayListener() {
            @Override
            public void onPlay(MultiPlayer.Track track) {
                if (track == null) {
                    onPlayListener.onPlay(null);
                    return;
                }
                DialogCue cue = getCueByTrack(track);
                if (cue != null) {
                    onPlayListener.onPlay(cue);
                }

            }
        });
    }

    synchronized public void setComputerPart(Set<String> computerPart) {
        this.computerPart = computerPart;
        setForRefresh(getTracks());
    }

    private List<MultiPlayer.Track> getTracks() {
        List<MultiPlayer.Track> tracks = new ArrayList<>();
        if (computerPart == null) {
            return tracks;
        }
        for (DialogCue cue : cues) {
            if (computerPart.contains(cue.getCharacterName())) {
                tracks.add(new MultiPlayer.Track(cue.getId(), cue.getFilename()));
            }
        }
        return tracks;
    }

    private DialogCue getCueByTrack(MultiPlayer.Track track) {
        if (track == null) {
            return null;
        }
        for (DialogCue cue : cues) {
            if (cue.getId().equals(track.id)) {
                return cue;
            }
        }
        return null;
    }

    @Override
    public int getGap(MultiPlayer.Track previous, MultiPlayer.Track current) {
        int gap = 0;
        DialogCue currentCue = getCueByTrack(current);
        DialogCue previousCue = getCueByTrack(previous);
        if (currentCue == null || previousCue == null) {
            return 0;
        }
        int previousPos = previousCue.getPosition();
        for (int i = currentCue.getPosition() - 2; i >= previousPos; i--) {
            gap += cues.get(i).getText().length();
        }

        return (gap > 0 ? (int) (500 * Math.log(gap)) : 0);
    }

}