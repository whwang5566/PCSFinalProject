package com.example.pcsfinalproject;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;
import org.mcnlab.lib.smscommunicate.CommandHandler;
import org.mcnlab.lib.smscommunicate.Recorder;

public class BoyuMainActivity extends FragmentActivity {
	public final static String LOG_TAG = "BoyuMainActivity";
	public final static String EXECUTOR_SEARCH_PHONE_TAG = "A";
	public final static String EXECUTOR_SEARCH_NAME_TAG = "B";
	
	//data
	final int PICK_CONTACT = 1;
		
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.boyu_activity_main);
        setContentView(R.layout.boyu_main_fragment);

        //UserDefined.filter = "$BOYU";
        
        //init RMS
        Recorder.init(this,LOG_TAG);
        CommandHandler.init(this);
        
        ExecutorForSearchPhone excutor = new ExecutorForSearchPhone();
        ExecutorForSearchName excutor2 = new ExecutorForSearchName();
        //set activity
        excutor.setActivity(this);
        excutor2.setActivity(this);
        
        CommandHandler.getSharedCommandHandler().addExecutor(EXECUTOR_SEARCH_PHONE_TAG,excutor);
        CommandHandler.getSharedCommandHandler().addExecutor(EXECUTOR_SEARCH_NAME_TAG,excutor2);
		
        //fragment view
     
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //fragment 1
        tabHost.addTab(tabHost.newTabSpec("Search Phone")
                              .setIndicator("Search Phone"), 
                       SearchPhoneFragment.class, 
                       null);
        
        //fragment 2
        tabHost.addTab(tabHost.newTabSpec("Search Name")
                              .setIndicator("Search Name"), 
                       SearchNameFragment.class, 
                       null);
        
      //fragment 3
       tabHost.addTab(tabHost.newTabSpec("Search History")
                              .setIndicator("Search History"), 
                       SearchHistoryFragment.class, 
                       null);
        
        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
