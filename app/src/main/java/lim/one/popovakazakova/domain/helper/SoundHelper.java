package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Section;
import lim.one.popovakazakova.domain.Sound;

public class SoundHelper implements ISectionHelper {
    SQLiteDatabase db;
    private String[] allColumns = {"_id", "lesson_id", "title", "content"};
    private static final String tableName = "sound";

    public SoundHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Sound> getAllSounds(Lesson lesson) {
        List<Sound> sounds = new ArrayList<Sound>();


        Cursor cursor = db.rawQuery("select " + getColumnsNames("") + " " +
                        "from " + tableName + " " +
                        "where lesson_id=?",
                new String[]{lesson.getId().toString()});
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


    public boolean hasSounds(Lesson lesson) {
        Cursor cursor = db.rawQuery("select count(*) from " + tableName + " where lesson_id = ?",
                new String[]{lesson.getId().toString()});
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @Override
    public List<Section> getSections(Lesson lesson) {
        List<Section> sections = new ArrayList<>();

        if (hasSounds(lesson)) {
            sections.add(new Section("Звуки"));
        }
        return sections;
    }
}
