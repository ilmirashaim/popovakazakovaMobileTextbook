package lim.one.popovakazakova;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.helper.DbHelper;
import lim.one.popovakazakova.section.SectionHelper;

public class EbookApplication extends Application {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private SectionHelper sectionHelper;
    private DomainHelperRegistry domainHelperRegistry;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }


    private void setupDatabase() {
        dbHelper = new DbHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        domainHelperRegistry = new DomainHelperRegistry();
    }

    SQLiteDatabase getDatabase() {
        return db;
    }

    public void setSectionHelper(SectionHelper sectionHelper){
        this.sectionHelper = sectionHelper;
    }

    public SectionHelper getSectionHelper(){
        return sectionHelper;
    }

    public void registerHelper(Object helper){
        domainHelperRegistry.registerHelper(helper);
    }

    public <T> T getHelper(Class<T> tClass){
        return (T)domainHelperRegistry.getHelper(tClass);
    }



    private class DomainHelperRegistry{
        List<Object> helpers = new ArrayList<>();

        public void registerHelper(Object helper){
            helpers.add(helper);
        }

        public Object getHelper(Class cl){
            for(Object h:helpers){
                if(h.getClass().isAssignableFrom(cl)){
                    return h;
                }
            }
            return null;
        }
    }
}