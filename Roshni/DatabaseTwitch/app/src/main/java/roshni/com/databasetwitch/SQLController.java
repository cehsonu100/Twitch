package roshni.com.databasetwitch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.SQLException;



/**
 * Created by Roshni on 4/9/2016.
 */
public class SQLController {
    private DBhelper dBHelper;
    private Context ourContex;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        this.ourContex=c;
    }

    public SQLController open()  throws android.database.SQLException{
        dBHelper = new DBhelper(ourContex);
        database = dBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dBHelper.close();
    }

    public void insert (String name, String location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBhelper.NAME,name);
        contentValues.put(DBhelper.LOCATION,location);
        database.insert(DBhelper.TABLE_NAME, null, contentValues);
        Log.i("Insert staus ","inserted");
    }

    public Cursor fetch() {
        String[] columns=new String[] {DBhelper._ID,DBhelper.NAME,DBhelper.LOCATION};
        Cursor cursor = database.query(DBhelper.TABLE_NAME,columns,null,null,null,null,null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
