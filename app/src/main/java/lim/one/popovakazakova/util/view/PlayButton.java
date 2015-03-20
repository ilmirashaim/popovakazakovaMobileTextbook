package lim.one.popovakazakova.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import lim.one.popovakazakova.R;

public class PlayButton extends Button {
    private boolean isPlaying = false;

    private static final int[] STATE_PLAYING = {R.attr.state_playing};

    public interface OnStateChangeListener {
        public void onPlay();

        public void onPause();
    }

    public OnStateChangeListener getOnStateChangeListener() {
        return onStateChangeListener;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    private OnStateChangeListener onStateChangeListener;


    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    setBackgroundResource(R.drawable.ic_av_play_arrow);
                    isPlaying = false;
                    onStateChangeListener.onPause();
                } else {
                    setBackgroundResource(R.drawable.ic_av_pause);
                    isPlaying = true;
                    onStateChangeListener.onPlay();
                }
            }
        });
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isPlaying) {
            mergeDrawableStates(drawableState, STATE_PLAYING);
        }
        return drawableState;
    }

    public void onFinished(){
        isPlaying = false;
        setBackgroundResource(R.drawable.ic_av_play_arrow);
    }
}
