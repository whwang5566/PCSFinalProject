package com.example.pcsfinalproject;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Recorder;

public class BoyuMainActivity extends Activity {
	public final static String LOG_TAG = "BoyuMainActivity";
	public final static String EXECUTOR_TAG = "ExcutorBoyu";
	protected Button boyuButton;
	protected TextView phoneNumTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boyu_activity_main);
        
        //
        //UserDefined.filter = "$BOYU";
        
        //init
        Recorder.init(this,LOG_TAG);
        CommandHandler.init(this);
        
        CommandHandler.getSharedCommandHandler().addExecutor(EXECUTOR_TAG,new ExecutorBoyu());
        
        //get views 
        getViews();
        
        //set events
        setEvents();
        
    }
    
    //set views
    protected void getViews(){
    	boyuButton = (Button)this.findViewById(R.id.boyoButton);
    	phoneNumTextView = (TextView)this.findViewById(R.id.phoneEditText);
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
				
				//hdlr.execute("BOYUUUUU", device_id, 0, null);
				hdlr.execute(EXECUTOR_TAG, device_id, 0, null);
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
    
}
