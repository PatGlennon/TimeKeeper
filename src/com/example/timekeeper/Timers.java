package com.example.timekeeper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Timers extends Activity {
	
	static final String MSTRING = "";
	
	private ArrayList<TimerPanel> tPanelGlobal;
	private SharedPreferences mPrefs;
	private String mString;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	  setContentView(R.layout.activity_timers);

    	  ListView myListView = (ListView)findViewById(R.id.listview);
    	  final EditText editText = (EditText)findViewById(R.id.editText);
    	  final ArrayList<TimerPanel> tPanel = new ArrayList<TimerPanel>();
    	  final ArrayAdapter<TimerPanel> aa;
    	  aa = new AddArrayAdapter(this, tPanel);
    	  myListView.setAdapter(aa);
    	  Button button = (Button) findViewById(R.id.plus);
    	  
    	  mPrefs = getPreferences(MODE_PRIVATE);
    	  mString = mPrefs.getString("info", MSTRING);
    	  
    	  //editText.setText(mString);
    	  if (!mString.equals("")){
	    	  String[] data1 = mString.split("[*]");
			  String[] persistData = new String[4];
			  
			  for (int i = 0; i < data1.length; i++){
				  persistData = data1[i].split("[&]");
				  
				  String array[] = persistData[1].split(":");
	            	long millis = Integer.parseInt(array[0])*60*1000+Integer.parseInt(array[1]) * 1000;
	          	  	millis += (SystemClock.uptimeMillis() - Long.parseLong(persistData[3]));
	          	  	String hms = String.format("%02d:%02d", 
	          	  			TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	          	  			TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	
	            if (persistData[2].equals("true")){
	          	  	tPanel.add(new TimerPanel(persistData[0], hms, persistData[2]));
	            }else{
			    	tPanel.add(new TimerPanel(persistData[0], persistData[1], persistData[2]));
	            }
				  aa.notifyDataSetChanged();
			  }
    	  }
    	  
 
    	
    	  button.setOnClickListener(new OnClickListener() {
    		  public void onClick(View v) {
    			  if (editText.getText().toString().trim().equals("")){
    				  Toast toast = Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_LONG);
    				  toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
    				  toast.show();
    				  editText.setText("");
    			 }else{
    				  tPanel.add(new TimerPanel(editText.getText().toString()));
    				  aa.notifyDataSetChanged();
    				  editText.setText("");
    			  }
    		  }
    	  });
    	  tPanelGlobal = tPanel;
    }
    @Override
    public void onPause(){
    	super.onPause();
	    StringBuffer s = new StringBuffer("");
	    for (int i = 0; i < tPanelGlobal.size(); i++){
	    	s.append(tPanelGlobal.get(i).getName() + "&" + tPanelGlobal.get(i).getTime() + "&" + 
	    			tPanelGlobal.get(i).isOnStr() + "&" + SystemClock.uptimeMillis() + "*");
	    }
    	SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("info",s.toString());
        ed.commit();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_timers, menu);
        return true;
    }
    
}
