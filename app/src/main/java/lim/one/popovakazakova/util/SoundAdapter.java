package lim.one.popovakazakova.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import lim.one.popovakazakova.domain.Sound;

public class SoundAdapter extends FragmentStatePagerAdapter {
    List<Sound> sounds;
    public SoundAdapter(FragmentManager fm, List<Sound> sounds) {
        super(fm);
        this.sounds = sounds;
    }

    @Override
    public Fragment getItem(int position) {
        return SoundFragment.newInstance(sounds.get(position));
    }

    @Override
    public int getCount() {
        return sounds.size();
    }
}