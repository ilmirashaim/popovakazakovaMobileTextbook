package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Sound;

public class SoundHelper {
    SQLiteDatabase db;
    private String[] allColumns = {"_id", "lesson_id", "title", "content"};
    private static final String tableName = "sound";

    public SoundHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Sound> getAllSounds(Lesson lesson) {
        List<Sound> sounds = new ArrayList<Sound>();


        Cursor cursor = db.rawQuery("select s._id, el.lesson_id, s.title, s.content " +
                        "from sound s, lesson_element el " +
                        "where el._id=s._id and " +
                        "el.lesson_id=?",
                new String[]{lesson.getId().toString()});
        Log.d("starting query: ", cursor.getCount() + "");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sound sound = cursorToSound(cursor);
            sounds.add(sound);
            cursor.moveToNext();
        }
        cursor.close();
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

        return sound;
    }
}
