package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.ReadingExercise;

public class ReadingExerciseHelper {
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "lesson_id", "content"
    };
    private static final String tableName = "reading_exercise";

    public ReadingExerciseHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<ReadingExercise> getExercises(Lesson lesson) {
        Cursor cursor = db.query(tableName,
                allColumns,
                "lesson_id=?",
                new String[]{lesson.getId().toString()},
                null, null, null, null);
        return cursorToReadingExerciseList(cursor);
    }

    public List<ReadingExercise> getAllExercises() {
        Cursor cursor = db.query(tableName,
                allColumns,
                null, null, null, null, null, null);
        return cursorToReadingExerciseList(cursor);
    }

    private List<ReadingExercise> cursorToReadingExerciseList(Cursor cursor) {
        List<ReadingExercise> exercises = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ReadingExercise readingExercise = cursorToReadingExercise(cursor);
            cursor.moveToNext();
            exercises.add(readingExercise);
        }
        cursor.close();
        return exercises;
    }

    private ReadingExercise cursorToReadingExercise(Cursor cursor) {
        ReadingExercise exercise = new ReadingExercise();
        exercise.setId(cursor.getLong(0));
        exercise.setLessonId(cursor.getLong(1));
        exercise.setContent(cursor.getString(2));

        return exercise;
    }

}
