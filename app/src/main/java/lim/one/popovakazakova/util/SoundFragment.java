package lim.one.popovakazakova.util;


import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class SoundFragment extends SimpleListFragment<Sound> {

    public static SoundFragment newInstance(List<Sound> sounds) {
        SoundFragment f = new SoundFragment();
        f.setElements(sounds);
        f.setListViewId(R.layout.fragment_sound_list);
        f.setListElementViewId(R.layout.fragment_sound);
        return f;
    }


    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView soundContent = (TextView) row.findViewById(R.id.sound_content);
        TextView soundTitle = (TextView) row.findViewById(R.id.sound_title);
        Sound sound = getElements().get(position);
        soundContent.setText(sound.getContent());
        soundTitle.setText(sound.getTitle());
    }

}
