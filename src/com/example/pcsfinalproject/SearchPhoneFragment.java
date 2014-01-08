package com.example.pcsfinalproject;

import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Recorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SearchPhoneFragment extends Fragment {
	//data
	protected Button boyuButton;
	protected EditText phoneNumTextView;
	protected EditText findNameTextView;
	
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	  }

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 
	    return inflater.inflate(R.layout.boyu_search_phone_fragment, container, false);
	  }
	  
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    //get views 
	    getViews();
	     
	    //set events
	    setEvents();
	    
	  }
	  
	//set views
	    protected void getViews(){
	    	boyuButton = (Button)getView().findViewById(R.id.boyoButton);
	    	phoneNumTextView = (EditText)getView().findViewById(R.id.phoneEditText);
	    	findNameTextView = (EditText)getView().findViewById(R.id.findNameEditText);
	    }
	    
	    //set events
	    protected void setEvents(){
	    	boyuButton.setOnClickListener(new OnClickListener()
	    	{

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					//get phone num
					String phoneNum = phoneNumTextView.getText().toString();
					Recorder recorder = Recorder.getSharedRecorder();
					CommandHandler hdlr = CommandHandler.getSharedCommandHandler();
					SQLiteDatabase db = recorder.getWritableDatabase();
					int device_id = recorder.getDeviceIdByPhonenumberOrCreate(db,phoneNum);
					db.close();
					
					//init send data
					String findName = findNameTextView.getText().toString();
					JSONObject jsonObj = new JSONObject();
					
					try {
						jsonObj.put("findName", findName);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//hdlr.execute(EXECUTOR_TAG, device_id, 0, null);
					hdlr.execute(BoyuMainActivity.EXECUTOR_SEARCH_PHONE_TAG, device_id, 0, jsonObj);
				}
	    		
	    	});
	    }
	    
}
