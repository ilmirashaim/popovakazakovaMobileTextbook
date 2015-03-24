package lim.one.popovakazakova.util.common;

import lim.one.popovakazakova.util.view.PlayButton;

public class PlayButtonMultiPlayerConnector implements MultiPlayer.OnFinishedListener,
        PlayButton.OnStateChangeListener {
    PlayButton playButton;
    MultiPlayer multiPlayer;
    @Override
    public void onPlay() {
        if(multiPlayer != null) {
            multiPlayer.play();
        }
    }

    @Override
    public void onPause() {
        if(multiPlayer != null) {
            multiPlayer.pause();
        }
    }


    public PlayButtonMultiPlayerConnector(PlayButton playButton, MultiPlayer multiPlayer) {
        this.multiPlayer = multiPlayer;
        this.playButton = playButton;
        playButton.setOnStateChangeListener(this);
        multiPlayer.setOnFinishedListener(this);
    }

    @Override
    public void onFinished() {
        if(playButton != null) {
            playButton.onFinished();
        }
    }
}