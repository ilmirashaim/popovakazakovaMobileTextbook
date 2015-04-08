package lim.one.popovakazakova.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

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


}
