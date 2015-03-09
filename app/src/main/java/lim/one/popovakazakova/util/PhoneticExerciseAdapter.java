package lim.one.popovakazakova.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Iterator;
import java.util.List;

public class PhoneticExerciseAdapter extends FragmentStatePagerAdapter {
    List<SoundInfo> soundInfos;

    public PhoneticExerciseAdapter(FragmentManager fm, List<SoundInfo> soundInfos) {
        super(fm);
        this.soundInfos = soundInfos;
    }

    @Override
    public Fragment getItem(int position) {
        int s = 0;
        Iterator<SoundInfo> soundInfoIterator = soundInfos.iterator();
        SoundInfo soundInfo = null;
        while (s <= position && soundInfoIterator.hasNext()) {
            soundInfo = soundInfoIterator.next();
            s += soundInfo.getChildrenSize();
        }
        int posInInfo = position - (s - soundInfo.getChildrenSize());
        Fragment fragment = soundInfo.getFragment(posInInfo);
        return fragment;
    }

    @Override
    public int getCount() {
        int s = 0;
        for (SoundInfo soundInfo : soundInfos) {
            s += soundInfo.getChildrenSize();
        }
        return s;
    }

}
