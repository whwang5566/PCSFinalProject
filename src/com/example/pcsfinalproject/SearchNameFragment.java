package com.example.pcsfinalproject;

import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Recorder;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SearchNameFragment extends Fragment {
	
	//data
	protected Button searchButton;
	protected EditText phoneNumTextView;
	protected EditText findNameTextView;
		
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);

	  }

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    return inflater.inflate(R.layout.boyu_search_name_fragment, container, false);
	  }
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    //get views 
	    getViews();
	     
	    //set events
	    setEvents();
	    
	  }
	  
	  //get views
	  protected void getViews(){
		  	searchButton = (Button)getView().findViewById(R.id.boyoButton2);
	    	phoneNumTextView = (EditText)getView().findViewById(R.id.phoneEditText2);
	    	findNameTextView = (EditText)getView().findViewById(R.id.findNameEditText2);
	    }
	    
	    //set events
	    protected void setEvents(){
	    	searchButton.setOnClickListener(new OnClickListener()
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
					String findNumber = findNameTextView.getText().toString();
					JSONObject jsonObj = new JSONObject();
					findNumber = findNumber.replaceAll("\\s","");
					
					try {
						jsonObj.put("findNumber", findNumber);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//hdlr.execute(EXECUTOR_TAG, device_id, 0, null);
					hdlr.execute(BoyuMainActivity.EXECUTOR_SEARCH_NAME_TAG, device_id, 0, jsonObj);
				}
	    		
	    	});
	    }
}
