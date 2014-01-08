package com.example.pcsfinalproject;

import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Executor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class ExecutorForSearchName implements Executor{
	//data
	//final int PICK_CONTACT = 1;
	private AlertDialog dialog;
	//activity
	private Activity activity;
	
	//set activity
	public void setActivity(Activity activity)
	{
		this.activity = activity;
		
		//dialog
	    dialog = getAlertDialog("通知","");
	  
	}
	
	@Override
	public JSONObject execute(Context context, int device_id, int count, JSONObject user_json) {
		// TODO Auto-generated method stub
		
		//debug
		Log.d("Executor","Count = "+count);
		if(user_json != null) Log.d("Debug","Excutor Name Count :"+count+" Json :"+user_json.toString());
			
		switch(count)
		{
			case 0:
				
				return user_json;
				//return new JSONObject();
			case 1:
				final int device_id_closure = device_id;
				
				String findNumber = "";
				//get find name
				if(user_json!=null)
				{
					//get send data
					try {
						findNumber = user_json.get("findNumber").toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//get contact
				JSONObject result = new JSONObject();
				 
				if(activity != null) result = getName(activity.getContentResolver(),findNumber); 
				
				Log.d("Debug","Send Name Result:"+result.toString());
				//send
				CommandHandler.getSharedCommandHandler().execute(BoyuMainActivity.EXECUTOR_SEARCH_NAME_TAG, device_id_closure, 2, result);
				
				return null;
			case 2:
				
				return user_json;
				//return null;
				
			case 3:
				//receive number
				String name="";
				String phone="";
				
				if(user_json!=null)
				{
					//get send data
					try {
						name = user_json.get("name").toString();
						phone = user_json.get("phone").toString();
						
						//save in DB
						SearchHistorySQLiteHelper.getInstance(activity).addItem(name, phone);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
				dialog.setMessage("名稱："+name+" 電話號碼："+phone);
				dialog.show();
				
				return null;
			default:
				Log.d("Debug","default!");
				
				return null;
			
		}
		
		
		//return null;
		//return new JSONObject();
	}
	
	public JSONObject getName(ContentResolver cr,String findNumber)
	{
		Log.d("Debug","FindName:"+findNumber);
		
	    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	    
	    JSONObject result = new JSONObject();
	    
	    //answer
	    String answerName="查無此人";
	    String answerPhoneNumber=""+findNumber;
	   
	    while (phones.moveToNext())
	    {
	    	//
	    	String name = "";
	    	String phoneNumber = "";
	    	name = name.concat(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
	    	phoneNumber = phoneNumber.concat(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
	    	
	    	//remove space
	    	name = name.replaceAll("\\s","");
	    	phoneNumber = phoneNumber.replaceAll("\\s","");
	    
	    	//Log.d("Debug","Name:"+name);
	    	//Log.d("Debug","Number:"+phoneNumber);
	      
	    	//check name
	    	if(findNumber.compareTo(phoneNumber) == 0)
	    	{
	    			//save answer
	    			answerName = name;
	    			answerPhoneNumber = phoneNumber;
	    	}
	    }
	             
	    phones.close();
	    
	   
	    try {
	    	result.put("phone", answerPhoneNumber);
	    	result.put("name", answerName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    return result;
	}
	
	private AlertDialog getAlertDialog(String title,String message){
        //new Builder
        Builder builder = new AlertDialog.Builder(activity);
        //Dialog title
        builder.setTitle(title);
        
        //Dialog  
        builder.setMessage(message);
        
        //Positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        
        return builder.create();
    }
}
