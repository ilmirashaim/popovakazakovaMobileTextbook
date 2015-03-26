package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;

public class SoundAdapter extends ArrayAdapter<Sound> {
    List<Sound> sounds;
    Context context;
    int layoutResourceId;

    public SoundAdapter(Context context, int layoutResourceId, List<Sound> sounds) {
        super(context, layoutResourceId, sounds);
        this.sounds = sounds;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            TextView soundContent = (TextView) row.findViewById(R.id.sound_content);
            TextView soundTitle = (TextView) row.findViewById(R.id.sound_title);
            Sound sound = sounds.get(position);
            soundContent.setText(sound.getContent());
            soundTitle.setText(sound.getTitle());
        }

        return row;
    }

    @Override
    public int getCount() {
        return sounds.size();
    }
}