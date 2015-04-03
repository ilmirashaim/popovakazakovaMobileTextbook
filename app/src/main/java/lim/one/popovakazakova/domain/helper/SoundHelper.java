package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Sound;

public class SoundHelper {
    SQLiteDatabase db;
    private String[] allColumns = {"_id", "lesson_id", "title", "content", "type"};
    private static final String tableName = "sound";

    public SoundHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Sound> getAllSounds(Lesson lesson) {
        Cursor cursor = db.query(tableName, allColumns,
                "lesson_id=?",
                new String[]{lesson.getId().toString()}, null, null, null, null);
        List<Sound> sounds = soundListFromCursor(cursor);
        return sounds;
    }

    private List<Sound> soundListFromCursor(Cursor cursor) {
        List<Sound> sounds = new ArrayList<Sound>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sound sound = cursorToSound(cursor);
            sounds.add(sound);
            cursor.moveToNext();
        }
        cursor.close();
        return sounds;
    }

    public List<Sound> getAllSounds() {


        Cursor cursor = db.query(tableName, allColumns,
                null, null, null, null, null, null);
        List<Sound> sounds = soundListFromCursor(cursor);
        return sounds;
    }

    private String getColumnsNames(String entityName) {
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String s : allColumns) {
            stringBuilder.append(entityName.isEmpty() ? "" : entityName + ".");
            stringBuilder.append(s);
            stringBuilder.append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }


    private Sound cursorToSound(Cursor cursor) {
        Sound sound = new Sound();
        sound.setId(cursor.getLong(0));
        sound.setLessonId(cursor.getLong(1));
        sound.setTitle(cursor.getString(2));
        sound.setContent(cursor.getString(3));
        sound.setType(cursor.getString(4));

        return sound;
    }


    public boolean hasSounds(Lesson lesson) {
        Cursor cursor = db.rawQuery("select count(*) from " + tableName + " where lesson_id = ?",
                new String[]{lesson.getId().toString()});
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

}
