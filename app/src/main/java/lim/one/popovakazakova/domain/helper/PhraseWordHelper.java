package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhraseWord;

public class PhraseWordHelper{
    SQLiteDatabase db;
    private static final String[] allColumns = new String[]{
            "_id", "lesson_id", "word", "translation", "part_of_speech"
    };
    private static final String tableName = "phrase_word";

    public PhraseWordHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<PhraseWord> getPhraseWords(Lesson lesson) {
        List<PhraseWord> phraseWords = new ArrayList<>();
        Cursor cursor = db.query(tableName,
                allColumns,
                "lesson_id=?",
                new String[]{lesson.getId().toString()},
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhraseWord phraseWord = cursorToPhraseWord(cursor);
            cursor.moveToNext();
            phraseWords.add(phraseWord);
        }
        cursor.close();
        return phraseWords;
    }

    private PhraseWord cursorToPhraseWord(Cursor cursor) {
        PhraseWord phraseWord = new PhraseWord();
        phraseWord.setId(cursor.getLong(0));
        phraseWord.setLessonId(cursor.getLong(1));
        phraseWord.setWord(cursor.getString(2));
        phraseWord.setTranslation(cursor.getString(3));
        phraseWord.setPartOfSpeech(cursor.getString(4));

        return phraseWord;
    }

}
