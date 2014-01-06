package com.example.pcsfinalproject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Executor;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ExecutorBoyu implements Executor{
	//data
	final int PICK_CONTACT = 1;
	
	Activity activity;
	
	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}
	
	
	@Override
	public JSONObject execute(Context context, int device_id, int count, JSONObject user_json) {
		// TODO Auto-generated method stub
		
		Log.d("Executor","Count = "+count);
		if(user_json != null) Log.d("Debug","Count :"+count+"json :"+user_json.toString());
			
		switch(count)
		{
			case 0:
				
				return user_json;
				//return new JSONObject();
			case 1:
				final int device_id_closure = device_id;
				
				String findName = "";
				//get find name
				if(user_json!=null)
				{
					//get send data
					try {
						findName = user_json.get("findName").toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//get contact
				JSONObject result = new JSONObject();
				
				 ArrayList<JSONObject> itemArray = null;
				if(activity != null) itemArray = getNumber(activity.getContentResolver(),findName); 
				if(itemArray != null)
				{
					//Log.d("Debug","item array size:"+itemArray.size());
					
					int itemCount = 1;
					for(JSONObject item :itemArray)
					{
						try {
							Log.d("Debug","Object:"+item.toString());
							//result.put("phone_"+itemCount, item.getString("phone"));
							//result.put("name_"+itemCount, item.getString("name"));
							result.put("phone_"+itemCount, item.get("phone"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						itemCount++;
					}
				}
				
				//send
				CommandHandler.getSharedCommandHandler().execute(BoyuMainActivity.EXECUTOR_TAG, device_id_closure, 2, result);
				
				
				//return new JSONObject();
				return null;
				//return result;
				
			case 2:
				Log.d("Debug","2222222!");
				
				return user_json;
				//return null;
				
			case 3:
				Log.d("Debug","3333!");
				
				
				return null;
			default:
				Log.d("Debug","default!");
				
				return null;
			
		}
		
		
		//return null;
		//return new JSONObject();
	}
	
	public ArrayList<JSONObject> getNumber(ContentResolver cr,String findName)
	{
		Log.d("Debug","FindName:"+findName);
		
	    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	    
	    ArrayList<JSONObject> result = new ArrayList<JSONObject>();
	    
	    //int count = 0;
	    //ArrayList <String> numberList = new ArrayList<String>();
	    //ArrayList <String> nameList = new ArrayList<String>();
	    while (phones.moveToNext())
	    {
	    	//
	    	String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	    	String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

	    	//aa.add(phoneNumber);
	    	//Log.d("Debug","Name:"+name);
	    	//Log.d("Debug","Number:"+phoneNumber);
	      
	    	//check name
	    	if(name.contains(findName))
	    	{
	    		//count++;
	    		//nameList.add(name);
	    		//numberList.add(phoneNumber);
	    		JSONObject object = new JSONObject();
	    		try {
					object.put("name", name);
	    			//object.put("user_name", "boyu");
	    			
					object.put("phone", phoneNumber);
		    		result.add(object);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    }
	             
	    phones.close();
	    
	    return result;
	}
	
}
