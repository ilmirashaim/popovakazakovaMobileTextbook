package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Section;

public class PhoneticExerciseHelper implements ISectionHelper{
    SQLiteDatabase db;

    public PhoneticExerciseHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean hasPhoneticExercises(Lesson lesson){
        Cursor cursor = db.rawQuery("select count(*) from phonetic_exercise ex, sound s " +
                        "where ex.sound_id = s._id " +
                        "and s.lesson_id=?",
                new String[]{lesson.getId().toString()});
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @Override
    public List<Section> getSections(Lesson lesson) {
        List<Section> sections = new ArrayList<>();

        if(hasPhoneticExercises(lesson)){
            sections.add(new Section("Фонетические упражнения", this));
        }
        return sections;
    }
}
