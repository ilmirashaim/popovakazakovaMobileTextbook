package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.domain.SoundUsage;

public class SoundUsageHelper {
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "sound_id", "spelling", "examples", "position"
    };
    private static final String tableName = "sound_usage";

    public SoundUsageHelper(SQLiteDatabase db) {
        this.db = db;
    }
    public List<SoundUsage> getSoundUsages(Sound sound){
        List<SoundUsage> soundUsages = new ArrayList<>();
        Cursor cursor = db.query(tableName,
                allColumns,
                "sound_id=?",
                new String[]{sound.getId().toString()},
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            SoundUsage soundUsage = cursorToSoundUsage(cursor);
            cursor.moveToNext();
            soundUsages.add(soundUsage);
        }
        return soundUsages;
    }

    private SoundUsage cursorToSoundUsage(Cursor cursor){
        SoundUsage soundUsage = new SoundUsage();
        soundUsage.setId(cursor.getLong(0));
        soundUsage.setSoundId(cursor.getLong(1));
        soundUsage.setSpelling(cursor.getString(2));
        soundUsage.setExamples(cursor.getString(3));
        soundUsage.setPosition(cursor.getString(4));

        return soundUsage;
    }
}
