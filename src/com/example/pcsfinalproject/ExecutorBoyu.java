package com.example.pcsfinalproject;

import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Executor;
import android.content.Context;
import android.util.Log;

public class ExecutorBoyu implements Executor{

	@Override
	public JSONObject execute(Context context, int device_id, int count, JSONObject user_json) {
		// TODO Auto-generated method stub
		
		Log.d("Executor","Count = "+count);
		
		switch(count)
		{
			case 0:
				return new JSONObject();
			case 1:
				final int device_id_closure = device_id;
				
				JSONObject jsonObject = new JSONObject();
				try
				{
					jsonObject.put("Message", "kerker");
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
				
				//CommandHandler.getSharedCommandHandler().execute(BoyuMainActivity.EXECUTOR_TAG, device_id_closure, 2, jsonObject);
				
				
				return null;
			case 2:
				
				return user_json;
			case 3:
			default:
			
		}
		
		
		return null;
		//return new JSONObject();
	}
	
}
