package lim.one.popovakazakova.domain.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbHelper extends SQLiteOpenHelper {
    public static final int databaseVersion = 1;
    public static final String databaseName = "popovaKazakova.sqlite3";

    public DbHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
        Context mycontext = context;

        File dest = mycontext.getDatabasePath(databaseName);
//        if(dest.exists()){
//            return;
//        }
        try {
            InputStream in = mycontext.getAssets().open("databases/" + databaseName);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();


        } catch (IOException e) {
            Log.e("db", "can't copy db", e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
