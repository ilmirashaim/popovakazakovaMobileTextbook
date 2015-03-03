package lim.one.popovakazakova;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import lim.one.popovakazakova.domain.helper.DbHelper;

public class EbookApplication extends Application {

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }


    private void setupDatabase() {
        dbHelper = new DbHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
    }

    SQLiteDatabase getDatabase() {
        return db;
    }
}