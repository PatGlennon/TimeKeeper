package com.example.timekeeper;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AddArrayAdapter extends ArrayAdapter<TimerPanel> {
	
	  private final List<TimerPanel> list;
	  private final Activity context;

	  public AddArrayAdapter(Activity context, List<TimerPanel> list) {
	    super(context, R.layout.timerlayout, list);
	    this.context = context;
	    this.list = list;
	  }

	  static class ViewHolder {
	    protected TextView text;
	    protected Chronometer chron;
	    protected ToggleButton tog;
	    protected String elapsedTime;
	    protected Button reset, del;
	  }

	  @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	    View view = null;
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.timerlayout, null);
	      final ViewHolder viewHolder = new ViewHolder();
	      viewHolder.text = (TextView) view.findViewById(R.id.timerText);
	      viewHolder.chron = (Chronometer) view.findViewById(R.id.chron);
	      viewHolder.tog = (ToggleButton) view.findViewById(R.id.toggle);
	      viewHolder.reset = (Button) view.findViewById(R.id.reset);
	      viewHolder.elapsedTime = list.get(position).getTime();
	      viewHolder.chron.setText(viewHolder.elapsedTime);
	      viewHolder.del = (Button) view.findViewById(R.id.del);
	      
	      viewHolder.del.setOnClickListener(new CompoundButton.OnClickListener() {
	            public void onClick(View v) {
	            	if (!viewHolder.tog.isChecked()){
		            	list.get(position).setTime("00:00");
		            	list.remove(position);
		            	notifyDataSetChanged();
	            	}
	            }
	      });
	      
	      viewHolder.reset.setOnClickListener(new CompoundButton.OnClickListener() {
	            public void onClick(View v) {
	            	if (!viewHolder.tog.isChecked()){
	            		viewHolder.elapsedTime = "00:00";
	            		viewHolder.chron.setText("00:00");
	            	}
	            }
	      });
	      
	      viewHolder.tog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	            public void onCheckedChanged(CompoundButton buttonView, boolean isOn) {
	              TimerPanel element = (TimerPanel) viewHolder.tog.getTag();
	              element.setOn(buttonView.isChecked());
	              
	              if (element.isOn() == true){
	          		int stoppedMilli = 0;
	          		String array[] = viewHolder.elapsedTime.split(":");
	          		stoppedMilli = Integer.parseInt(array[0])*60*1000+Integer.parseInt(array[1]) * 1000;
	          		viewHolder.chron.setBase(SystemClock.elapsedRealtime() - stoppedMilli);
	          		viewHolder.chron.start();
	          	  }else {
	          		viewHolder.elapsedTime = (String) viewHolder.chron.getText();
	          		viewHolder.chron.stop();
	              }
	            }
	      });
	      
	      viewHolder.chron.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
	          public void onChronometerTick(Chronometer c) {
	              list.get(position).setTime(c.getText().toString());
	          }
	      });
	      view.setTag(viewHolder);
	      viewHolder.tog.setTag(list.get(position));
	    } else {
	      view = convertView;
	      ((ViewHolder) view.getTag()).tog.setTag(list.get(position));
	    }
	    ViewHolder holder = (ViewHolder) view.getTag();
	    list.get(position).setTime(holder.elapsedTime);
	    holder.text.setText(list.get(position).getName());
	    holder.tog.setChecked(list.get(position).isOn());
	    return view;
	  }

}
