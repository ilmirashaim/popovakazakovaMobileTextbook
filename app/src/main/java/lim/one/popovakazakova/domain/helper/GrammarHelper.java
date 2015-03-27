package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Grammar;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.ReadingRule;

public class GrammarHelper {
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "lesson_id", "text_html", "title"
    };
    private static final String tableName = "grammar";

    public GrammarHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Grammar> getGrammars(Lesson lesson) {
        List<Grammar> grammars = new ArrayList<>();
        Cursor cursor = db.query(tableName,
                allColumns,
                "lesson_id=?",
                new String[]{lesson.getId().toString()},
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Grammar grammar = cursorToGrammar(cursor);
            cursor.moveToNext();
            grammars.add(grammar);
        }
        cursor.close();
        return grammars;
    }

    private Grammar cursorToGrammar(Cursor cursor) {
        Grammar grammar = new Grammar();
        grammar.setId(cursor.getLong(0));
        grammar.setLessonId(cursor.getLong(1));
        grammar.setTextHtml(cursor.getString(2));
        grammar.setTitle(cursor.getString(3));

        return grammar;
    }

}
