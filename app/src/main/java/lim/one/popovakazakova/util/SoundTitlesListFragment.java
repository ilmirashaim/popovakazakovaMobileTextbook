package lim.one.popovakazakova.util;


import android.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class SoundTitlesListFragment extends SimpleListFragment<Sound> {
    int visible = -1;

    public static SoundTitlesListFragment newInstance(List<Sound> sounds) {
        SoundTitlesListFragment f = new SoundTitlesListFragment();
        f.setElements(sounds);
        f.setListViewId(R.layout.fragment_sound_list);
        f.setListElementViewId(R.layout.fragment_sound_title);
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView soundContent = (TextView) v.findViewById(R.id.sound_content);
        CardView cardView = (CardView) v.findViewById(R.id.card_view);
        int soundContentVisibility = soundContent.getVisibility();
        if(soundContentVisibility== View.VISIBLE){
            soundContent.setVisibility(View.GONE);
            cardView.setCardElevation(0);
        }else{
            visible = position;
            soundContent.setVisibility(View.VISIBLE);
            cardView.setCardElevation(getResources().getDimension(R.dimen.card_elevation));
        }
    }
}
