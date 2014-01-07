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

public class ExecutorForSearchPhone implements Executor{
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
		if(user_json != null) Log.d("Debug","Excutor Phone Count :"+count+" Json :"+user_json.toString());
			
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
				 
				if(activity != null) result = getNumber(activity.getContentResolver(),findName); 
				
				Log.d("Debug","Send Phone Result:"+result.toString());
				//send
				CommandHandler.getSharedCommandHandler().execute(BoyuMainActivity.EXECUTOR_SEARCH_PHONE_TAG, device_id_closure, 2, result);
				
				return null;
			case 2:
				
				return user_json;
				//return null;
				
			case 3:
				//receive 
				String name="";
				String phone="";
				if(user_json!=null)
				{
					//get send data
					try {
						name = user_json.get("name").toString();
						phone = user_json.get("phone").toString();
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
	
	public JSONObject getNumber(ContentResolver cr,String findName)
	{
		Log.d("Debug","FindName:"+findName);
		
	    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	    
	    JSONObject result = new JSONObject();
	    
	    //answer
	    String answerName="";
	    String answerPhoneNumber="";
	    int minValue = Integer.MAX_VALUE;
	    
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
	    	if(name.contains(findName))
	    	{
	    		//if contains , find most likely
	    		int value = findName.compareTo(name);
	    		if(value < minValue)
	    		{
	    			//save min compare value
	    			minValue = value;
	    			
	    			//save answer
	    			answerName = name;
	    			answerPhoneNumber = phoneNumber;
	    		}
	    	}
	    }
	             
	    phones.close();
	    
	    //check find or not
	    if(minValue == Integer.MAX_VALUE)
	    {
	    	try {
	    		result.put("phone", "");
	    		result.put("name", "查無此人號碼");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else
	    {	
	    	try {
	    		result.put("phone", answerPhoneNumber);
	    		result.put("name", answerName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
