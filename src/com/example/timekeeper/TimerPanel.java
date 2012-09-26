package com.example.timekeeper;

public class TimerPanel{
	private String name;
	private String elapsedTime;
	private boolean isOn = false;
	
	  public TimerPanel(String name){
		  this.name = name;
		  this.elapsedTime = "00:00";
	  }
	  public TimerPanel(String name, String time, String isOn){
		  this.name = name;
		  this.elapsedTime = time;
		  
		  if (isOn.equals("true"))
			  this.isOn = true;
		  else
			  this.isOn = false;
	  }

	  public String getName() {
		  return name;
	  }

	  public void setName(String name) {
		  this.name = name;
	  }

	  public String getTime() {
		  return elapsedTime;
	  }

	  public void setTime(String time) {
		  this.elapsedTime = time;
	  }
	  public void reset(){
		  this.elapsedTime = "00:00";
	  }
	  public void setOn(Boolean b){
		  isOn = b;
	  }
	
	  public boolean isOn(){
		  return isOn;
	  }
	  public String isOnStr(){
		  if (isOn)
			  return "true";
		  else
			  return "false";
	  }
}
