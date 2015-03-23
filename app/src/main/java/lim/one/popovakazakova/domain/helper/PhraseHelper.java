package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;

public class PhraseHelper {
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "lesson_id", "text"
    };
    private static final String tableName = "phrase";

    public PhraseHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Phrase> getPhrases(Lesson lesson) {
        List<Phrase> phrases = new ArrayList<>();
        Cursor cursor = db.query(tableName,
                allColumns,
                "lesson_id=?",
                new String[]{lesson.getId().toString()},
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Phrase phrase = cursorToPhrase(cursor);
            cursor.moveToNext();
            phrases.add(phrase);
        }
        cursor.close();
        return phrases;
    }

    private Phrase cursorToPhrase(Cursor cursor) {
        Phrase phrase = new Phrase();
        phrase.setId(cursor.getLong(0));
        phrase.setLessonId(cursor.getLong(1));
        phrase.setText(cursor.getString(2));

        return phrase;
    }

}
