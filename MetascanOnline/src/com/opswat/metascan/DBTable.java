package com.opswat.metascan;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBTable {
   // Database creation SQL statement
	static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS scandata ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			"name TEXT, "+"dataid TEXT, "+
			"status TEXT )";

      public static void onCreate(SQLiteDatabase database) {
          database.execSQL(CREATE_TABLE);
      }
   
      public static void onUpgrade(SQLiteDatabase database, int oldVersion,
   int newVersion) {
         Log.w(DBTable.class.getName(), "Upgrading database from version "
           + oldVersion + " to " + newVersion
           + ", which will destroy all old data");
           database.execSQL("DROP TABLE IF EXISTS todo");
           onCreate(database);
         }
    }