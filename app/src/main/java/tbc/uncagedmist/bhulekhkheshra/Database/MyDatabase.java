package tbc.uncagedmist.bhulekhkheshra.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "bhulekh.db";
    private static final int DATABASE_VERSION = 2;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAllStateData()   {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("select * from stateList order by stateId");
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    public Cursor getStateByNames(String stateName) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from stateList WHERE stateName Like '%"+stateName+"%'";
        Cursor cursor = database.rawQuery(query, null);

        return cursor;
    }
}
