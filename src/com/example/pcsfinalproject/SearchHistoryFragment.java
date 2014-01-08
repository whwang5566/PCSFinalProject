package com.example.pcsfinalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchHistoryFragment extends Fragment {
	//data
	protected ListView listView;
	protected ArrayList<JSONObject> historyArray;
	
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	  }

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 
	    return inflater.inflate(R.layout.boyu_search_history_fragment, container, false);
	  }
	  
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    //get views 
	    getViews();
	     
	    //set events
	    setEvents();
	    
	    //init
	    historyArray = new ArrayList<JSONObject>();
	    
	  }
	  
	  //set views
	  protected void getViews(){
	    listView = (ListView)getView().findViewById(R.id.SearchHistorylistView);
	    	
	  }
	    
	  //set events
	  protected void setEvents(){
	    	
	  }
	  
	  @Override
	  public void onResume() {
	     
	     //get search history
		 historyArray = SearchHistorySQLiteHelper.getInstance(this.getActivity()).getSearchHistory();
		 
		 //print
		 //Log.d("Debug","History Array:"+historyArray.toString());
		 
		 //adapter
		 List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		 
		 // create the grid item mapping
		 String[] from = new String[] {"name", "phone"};
	     int[] to = new int[] { R.id.searchHistoryItemNameTextView, R.id.searchHistoryItemPhoneTextView};
	 
	        
		 for(int i = 0; i < historyArray.size(); i++){
		    	HashMap<String, String> map = new HashMap<String, String>();
		    	
		    	try {
					map.put("name",historyArray.get(i).getString("name"));
					map.put("phone",historyArray.get(i).getString("phone"));
				    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	fillMaps.add(map);
		 }
		    
		 // fill in the grid_item layout
		 SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), fillMaps, R.layout.list_item, from, to);
		 listView.setAdapter(adapter);
		    
	  
	     super.onResume();
	  }
	  
}
