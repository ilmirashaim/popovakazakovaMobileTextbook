package lim.one.popovakazakova.util;

import android.support.v4.app.Fragment;

import java.util.List;

import lim.one.popovakazakova.domain.PhoneticExercise;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.domain.SoundUsage;

public class SoundInfo {
    Sound sound;
    List<SoundUsage> soundUsages;
    List<PhoneticExercise> phoneticExercises;

    public SoundInfo(Sound sound, List<SoundUsage> soundUsages, List<PhoneticExercise> phoneticExercises) {
        this.sound = sound;
        this.soundUsages = soundUsages;
        this.phoneticExercises = phoneticExercises;
    }

    public int getChildrenSize() {
        return 1 + phoneticExercises.size();
    }

    public Fragment getFragment(int position) {
        Fragment f;
        if(position == 0){
            f = SoundUsageFragment.newInstance(sound, soundUsages);
        }else{
            f = PhoneticExerciseFragment.newInstance(phoneticExercises.get(position - 1));
        }
        return f;
    }
}
