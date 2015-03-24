package lim.one.popovakazakova.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import lim.one.popovakazakova.R;

public class PlayButton extends FloatingActionButton{

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
        setChecked(false);
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
                if (isChecked) {
                    onStateChangeListener.onPlay();
                } else {
                    onStateChangeListener.onPause();
                }
            }
        });
    }

    public void onFinished(){
        setChecked(false);
    }
}
