package com.example.pcsfinalproject;

import java.io.UnsupportedEncodingException;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Recorder;

public class BoyuMainActivity extends Activity {
	public final static String LOG_TAG = "BoyuMainActivity";
	public final static String EXECUTOR_TAG = "ExcutorBoyu";
	protected Button boyuButton;
	protected EditText phoneNumTextView;
	protected EditText findNameTextView;
	//data
	final int PICK_CONTACT = 1;
		
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boyu_activity_main);
        
        //
        //UserDefined.filter = "$BOYU";
        
        //init
        Recorder.init(this,LOG_TAG);
        CommandHandler.init(this);
        
        ExecutorBoyu excutor = new ExecutorBoyu();
        excutor.setActivity(this);
        
        CommandHandler.getSharedCommandHandler().addExecutor(EXECUTOR_TAG,excutor);
        
        //get views 
        getViews();
        
        //set events
        setEvents();
        
        //test
        String name = "«k¦~";
        //handle chinese string
		
		JSONObject temp = new JSONObject();
		
		try {
			temp.put("user_name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			Log.d("Debug","Name: 2"+temp.getString("user_name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("Debug","Error!");
		}
		
		
    }
    
    //set views
    protected void getViews(){
    	boyuButton = (Button)this.findViewById(R.id.boyoButton);
    	phoneNumTextView = (EditText)this.findViewById(R.id.phoneEditText);
    	findNameTextView = (EditText)this.findViewById(R.id.findNameEditText);
    }
    
    //set events
    protected void setEvents(){
    	boyuButton.setOnClickListener(new OnClickListener()
    	{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("Boyu","onClick");
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
				
				//hdlr.execute("BOYUUUUU", device_id, 0, null);
				//hdlr.execute(EXECUTOR_TAG, device_id, 0, null);
				hdlr.execute(EXECUTOR_TAG, device_id, 0, jsonObj);
				//hdlr.execute("WHERE", device_id, 1, new JSONObject());
			}
    		
    	});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*
    @Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	  super.onActivityResult(reqCode, resultCode, data);

	  switch (reqCode) {
	    case (PICK_CONTACT) :
	      if (resultCode == Activity.RESULT_OK) {
	        Uri contactData = data.getData();
	        Cursor c =  getContentResolver().query(contactData, null, null, null, null);
	        if (c.moveToFirst()) {
	          String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	          // TODO Whatever you want to do with the selected contact name.
	        }
	      }
	      break;
	  }
	}
	*/
    
}
