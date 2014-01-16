package com.opswat.metascan;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteDB extends SQLiteOpenHelper{

	private static final int version = 1;
	private static final String name = "FileDB";

	// table name
	private static final String TABLE_SCANDATA = "scandata";

	// Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DATAID = "dataid";
	private static final String KEY_STATUS = "status";

		
	private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_DATAID,KEY_STATUS};

    private static MySQLiteDB mysqlite = null;
    
    private static SQLiteDatabase db = null;


	private MySQLiteDB(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		mysqlite = this;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		DBTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		DBTable.onUpgrade(db, oldVersion, newVersion);
	}
	
	  // will return a singleton object of this class will as well open the connection for convenient

    public static MySQLiteDB getInstance(Context context){
        if(mysqlite==null){
        	mysqlite = new MySQLiteDB(context);
           openConnection();
          }
         return mysqlite;
      }
     // will be called only once when singleton is created
 
    private static void openConnection(){
          if ( db == null ){
                db = mysqlite.getWritableDatabase();
            }
      }
    // be sure to call this method by: DatabaseHelper.getInstance.closeConnecion() when application is closed by some means most likely
    // onDestroy method of application

     public synchronized void closeConnecion() {
          if(mysqlite!=null){
        	  mysqlite.close();
                 db.close(); 
                 mysqlite = null;
                db = null;
            }
        }
     

     
     //Methods for scanning files data
     public void getAll(){
    	 String selectQuery = "SELECT * FROM scandata";
     Cursor cursor = db.rawQuery(selectQuery, null);
     ArrayList<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();
     // looping through all rows and adding to list

     if (cursor.moveToFirst()) {
         do {
             HashMap<String, String> map = new HashMap<String, String>();
             for(int i=0; i<cursor.getColumnCount();i++)
             {
                 map.put(cursor.getColumnName(i), cursor.getString(i));
         		Log.d("main", cursor.getColumnName(i) + " "+ cursor.getString(i));

             }

             maplist.add(map);
         } while (cursor.moveToNext());
     }
     db.close();
     // return contact list
     //return maplist;
     }
	public void addFile(String name,String dataid, String status){
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_DATAID, dataid);
		values.put(KEY_STATUS, status);
		Log.d("main", name);
		db.insert(TABLE_SCANDATA, null, values);
	}
	public String[] fileStatus(String name){

		Cursor cursor = db.query(TABLE_SCANDATA, COLUMNS, "name = ?", new String[] { String.valueOf(name) } , null, null, null, null);
		if (cursor != null)
	        cursor.moveToFirst();
		else return null;
		if(cursor.getCount()!=0){
		String[] res = {cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)};
		return res;
		}
		else return null;
	}
	public void updateFile(String name, String dataid, String status){
		ContentValues values = new ContentValues();
		values.put(KEY_DATAID, dataid);
		values.put(KEY_STATUS, status);
		db.update(TABLE_SCANDATA, values, KEY_NAME+" =?", new String[] { String.valueOf(name) });
	}
	public void deleteFile(String name){
		db.delete(TABLE_SCANDATA, KEY_NAME+" =?", new String[] { String.valueOf(name) });
	}


}
