package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhoneticExercise;
import lim.one.popovakazakova.domain.Sound;

public class PhoneticExerciseHelper {
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "sound_id", "filename", "transcription", "title"
    };

    public PhoneticExerciseHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean hasPhoneticExercises(Lesson lesson) {
        Cursor cursor = db.rawQuery("select count(*) from phonetic_exercise ex, sound s " +
                        "where ex.sound_id = s._id " +
                        "and s.lesson_id=?",
                new String[]{lesson.getId().toString()});
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public List<PhoneticExercise> getPhoneticExercises(Sound sound) {
        List<PhoneticExercise> exercises = new ArrayList<>();
        Cursor cursor = db.query("phonetic_exercise",
                allColumns,
                "sound_id=?", new String[]{sound.getId().toString()},
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhoneticExercise phoneticExercise = cursorToPhoneticExercise(cursor);
            cursor.moveToNext();
            exercises.add(phoneticExercise);
        }
        cursor.close();
        return exercises;
    }

    private PhoneticExercise cursorToPhoneticExercise(Cursor cursor) {
        PhoneticExercise exercise = new PhoneticExercise();
        exercise.setId(cursor.getLong(0));
        exercise.setSoundId(cursor.getLong(1));
        exercise.setFilename(cursor.getString(2));
        exercise.setTranscription(cursor.getString(3));
        exercise.setTitle(cursor.getString(4));

        return exercise;
    }
}
