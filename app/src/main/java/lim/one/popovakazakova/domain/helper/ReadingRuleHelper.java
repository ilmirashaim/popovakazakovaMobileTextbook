package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.ReadingRule;

public class ReadingRuleHelper {
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "lesson_id", "text_html", "title"
    };
    private static final String tableName = "reading_rule";

    public ReadingRuleHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<ReadingRule> getReadingRules(Lesson lesson) {
        Cursor cursor = db.query(tableName,
                allColumns,
                "lesson_id=?",
                new String[]{lesson.getId().toString()},
                null, null, null, null);
        return cursorToList(cursor);
    }


    public List<ReadingRule> getAllReadingRules() {
        Cursor cursor = db.query(tableName,
                allColumns,
                null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    private List<ReadingRule> cursorToList(Cursor cursor) {
        List<ReadingRule> readingRules = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ReadingRule rule = cursorToReadingRule(cursor);
            cursor.moveToNext();
            readingRules.add(rule);
        }
        cursor.close();
        return readingRules;
    }



    private ReadingRule cursorToReadingRule(Cursor cursor) {
        ReadingRule readingRule = new ReadingRule();
        readingRule.setId(cursor.getLong(0));
        readingRule.setLessonId(cursor.getLong(1));
        readingRule.setTextHtml(cursor.getString(2));
        readingRule.setTitle(cursor.getString(3));

        return readingRule;
    }

}
