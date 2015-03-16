package lim.one.popovakazakova.domain.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Dialog;
import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.domain.Lesson;

public class DialogHelper {
    SQLiteDatabase db;
    public static final String tableName = "dialog";
    public static final String cueTableName = "dialog_cue";

    public static final String[] allColumns = {"_id", "lesson_id"};
    public static final String[] allCueColumns = {"_id", "dialog_id",
            "position", "character_name", "text"
    };

    public DialogHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Dialog> getDialogs(Lesson lesson) {
        List<Dialog> dialogs = new ArrayList<>();
        Cursor cursor = db.query(tableName, allColumns, "lesson_id=?",
                new String[]{lesson.getId().toString()}, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dialog dialog = cursorToDialog(cursor);
            dialogs.add(dialog);
            cursor.moveToNext();
        }
        cursor.close();
        return dialogs;
    }

    private Dialog cursorToDialog(Cursor cursor) {
        Dialog dialog = new Dialog();
        dialog.setId(cursor.getLong(0));
        dialog.setLessonId(cursor.getLong(1));
        return dialog;
    }

    public List<DialogCue> getDialogCues(Dialog dialog) {
        List<DialogCue> dialogCues = new ArrayList<>();
        Cursor cursor = db.query(cueTableName, allCueColumns, "dialog_id=?",
                new String[]{dialog.getId().toString()}, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DialogCue dialogCue = cursorToDialogCue(cursor);
            dialogCues.add(dialogCue);
            cursor.moveToNext();
        }
        cursor.close();
        return dialogCues;
    }

    private DialogCue cursorToDialogCue(Cursor cursor) {
        DialogCue dialogCue = new DialogCue();
        dialogCue.setId(cursor.getLong(0));
        dialogCue.setDialogId(cursor.getLong(1));
        dialogCue.setPosition(cursor.getLong(2));
        dialogCue.setCharacterName(cursor.getString(3));
        dialogCue.setText(cursor.getString(4));
        return dialogCue;
    }

    public boolean hasDialogs(Lesson lesson) {
        Cursor cursor = db.rawQuery("select count(*) from " + tableName + " where lesson_id = ?",
                new String[]{lesson.getId().toString()});
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

}
