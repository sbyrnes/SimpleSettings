package com.fogstack.simplesettings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*package*/ class SimpleSettingsDAO {
    private static final String DATABASE_NAME = "simplesettings.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SETTINGS_TABLE_NAME = "simple_settings";
    private static final String SETTINGS_TABLE_KEY_ROWID = "_id";
    private static final String SETTINGS_TABLE_KEY_KEY = "key";
    private static final String SETTINGS_TABLE_KEY_VALUE = "value";
    private static final String SETTINGS_TABLE_CREATE =
        "create table "+SETTINGS_TABLE_NAME+" ("+
        		SETTINGS_TABLE_KEY_ROWID + " integer primary key autoincrement, " +
        		SETTINGS_TABLE_KEY_KEY   + " text not null, " +
        		SETTINGS_TABLE_KEY_VALUE + " text not null);";
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	Log.i("SimpleSettings", "Creating database tables...");
            db.execSQL(SETTINGS_TABLE_CREATE);
        	Log.i("SimpleSettings", "Done. Tables created.");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	Log.i("SimpleSettings", "Upgrading database from version " + 
        												oldVersion + " to "
        												+ newVersion + ", which will destroy all old data");
        }
    }    
    
    public SimpleSettingsDAO(Context ctx)
    {
    	mCtx = ctx;
    }
    
    public static SimpleSettingsDAO getInstance(Context ctx) { return new SimpleSettingsDAO(ctx); }
    
    private synchronized void open() throws SQLException {
    	Log.i("SimpleSettings", "Opening database");
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public synchronized void close() {
    	Log.i("SimpleSettings", "Closing database");
        mDbHelper.close();
    }
    
    public void save(String key, Object value)
    {
    	open();
    	
        try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(SETTINGS_TABLE_KEY_KEY, key);
			initialValues.put(SETTINGS_TABLE_KEY_VALUE, value.toString());
			mDb.insert(SETTINGS_TABLE_NAME, null, initialValues);
		} catch (Exception e) {
			Log.e("SimpleSettings", "Unable to save setting", e);
		} finally {
	    	close();
		}
    }
    
    public String getString(String key)
    {
    	String result = null;
    	
    	open();
    	
    	try 
    	{
        	Log.i("SimpleSettings", "Fetching user profile...");
            Cursor cursor =  mDb.query(SETTINGS_TABLE_NAME, 
    			        				 new String[] {SETTINGS_TABLE_KEY_VALUE}, 
    			        				 SETTINGS_TABLE_KEY_KEY +"=?", 
    			        				 new String[] {key}, 
    			        				 null, null, null);
            
            // if we found nothing, return null;
            if(cursor.moveToFirst())
            {
                result = cursor.getString(cursor.getColumnIndex(SETTINGS_TABLE_KEY_VALUE));
            }
            
            cursor.close();
    	} finally {
    		close();
    	}
        
        return result;
    }
}
