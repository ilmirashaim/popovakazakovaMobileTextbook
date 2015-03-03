package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.exception.DatabaseException;

public class LessonHelper {
    SQLiteDatabase db;
    private String[] allColumns = {"_id", "title"};

    public LessonHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public Lesson getById(Long id) {
        throw new DatabaseException("not yet realized");
    }

    public List<Lesson> getAll() {
        List<Lesson> lessons = new ArrayList<Lesson>();

        Cursor cursor = db.query("lesson",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Lesson lesson = cursorToLesson(cursor);
            lessons.add(lesson);
            cursor.moveToNext();
        }
        cursor.close();
        return lessons;
    }


    private Lesson cursorToLesson(Cursor cursor) {
        Lesson lesson = new Lesson();
        lesson.setId(cursor.getLong(0));

        lesson.setTitle(cursor.getString(1));
        return lesson;
    }

}
