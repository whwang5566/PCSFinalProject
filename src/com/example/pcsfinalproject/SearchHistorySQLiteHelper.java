package com.example.pcsfinalproject;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SearchHistorySQLiteHelper extends SQLiteOpenHelper {
	private final static int _DBVersion = 1; //db version
	private final static String _DBName = "SearchHistory.db";  //db name
	private final static String _TableName = "History"; //table name
	private final static String _KeyName = "_name"; //key name
	private final static String _KeyPhone = "_phone"; //key name
	
	//self instance
	private static SearchHistorySQLiteHelper mInstance = null;
	
	//get instance
	static public synchronized SearchHistorySQLiteHelper getInstance(Context context)
	{
		if(null == mInstance)
		{
			mInstance = new SearchHistorySQLiteHelper(context);
		}
		
		return mInstance;
	}
	
	public SearchHistorySQLiteHelper(Context context) {
		super(context, _DBName, null, _DBVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		//create DB
		final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				_KeyName+" VARCHAR(50), " +
				_KeyPhone+" VARCHAR(20)" +
				");";
		
		db.execSQL(SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		final String SQL = "DROP TABLE " + _TableName;
		db.execSQL(SQL);
	}
	
	//add item
	public void addItem(String name,String phone){
		
		Log.d("Debug","Add Item to DB :"+name+" ,"+phone);
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(_KeyName, name);
		values.put(_KeyPhone, phone);
		
		db.insert(_TableName, null, values);
	}
	
	//get items
	public ArrayList<JSONObject> getSearchHistory(){
		 //db
		 SQLiteDatabase db = this.getReadableDatabase();
		 Cursor cursor = db.rawQuery("SELECT * FROM "+_TableName+" ORDER BY _id DESC", null);
		 
		 //save in array
		 ArrayList<JSONObject> historyArray = new ArrayList<JSONObject>();
		  
		 int rows_num = cursor.getCount();//get count
		 
		 if(rows_num != 0) {
			 cursor.moveToFirst();   //move to first
		  
			 for(int i=0; i<rows_num; i++) {
				 //json object
				 JSONObject item = new JSONObject();
				 
				 String valueName = cursor.getString(1);
				 String valuePhone = cursor.getString(2);
				 
				 try {
					item.put("name", valueName);
					item.put("phone", valuePhone);
				 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
				 
				 //put to array
				 historyArray.add(item);
		    
				 cursor.moveToNext();//move to next
			 }	
		  
		 }
		 
		 cursor.close(); //close Cursor
		 
		 return historyArray;
	}
	
}
