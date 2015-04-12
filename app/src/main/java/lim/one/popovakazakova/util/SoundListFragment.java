package lim.one.popovakazakova.util;

import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class SoundListFragment extends SimpleListFragment<Sound> {

    public static SoundListFragment newInstance(List<Sound> sounds) {
        SoundListFragment f = new SoundListFragment();
        f.setElements(sounds);
        f.setListViewId(R.layout.fragment_sound_list);
        f.setListElementViewId(R.layout.fragment_sound_list_item);
        return f;
    }


    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView soundContent = (TextView) row.findViewById(R.id.sound_content);
        TextView soundTitle = (TextView) row.findViewById(R.id.sound_title);
        Sound sound = getElements().get(position);
        soundContent.setText(sound.getContent());
        soundTitle.setText(sound.getTitle());
        soundTitle.getBackground().setColorFilter(getResources().getColor(
                getResources().getIdentifier(sound.getType(), "color", getActivity().getPackageName())
        ), PorterDuff.Mode.SRC);
    }

}
