package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.LessonElement;

public class LessonElementHelper {
    SQLiteDatabase db;

    public LessonElementHelper(SQLiteDatabase db) {
        this.db = db;
    }

    private String[] allColumns = { "_id", "lesson_id" };
    LessonElement getById(Long id){
        return new LessonElement();
    }

    Lesson getLesson(LessonElement element){
        return new Lesson();
    }


    public List<LessonElement> getElements(Lesson lesson){
        List<LessonElement> elements = new ArrayList<LessonElement>();

        Cursor cursor = db.query("lesson_element",
                allColumns, "where lesson_id = " + lesson.getId(), null, null, null, null);

        return elements;
    }
}
