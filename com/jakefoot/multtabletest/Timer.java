package com.jakefoot.multtabletest;


public class Timer implements Runnable
{
    long starttime = 0;
    long elapsed = 0;
    long totaltime = 0;
    public int i = 0;
    int minutes;
    double seconds;
    
    
    public Timer()
    {
	Gui.timerlabel.setText("00:00");
    }
    
    public void displayTime(long t)
    {
	totaltime = t;
	int min = (int) ((totaltime/1000000000)/60);
	double sec = (((double)totaltime/(double)1000000000)%(double)60);
	Gui.timerlabel.setText(String.format("%02d:%02.0f", min, sec));
    }
    
    public long getTime()
    {
	return totaltime;	
    }
    
    public void setTime(long tt)
    {
	totaltime = tt;
    }
    
    @Override
    public void run()
    {
	starttime = System.nanoTime();
	while (!Thread.currentThread().isInterrupted())
	{
	    elapsed = System.nanoTime() - starttime;
	    minutes = (int) (((elapsed+totaltime)/1000000000)/60);
	    seconds = (((double)(elapsed+totaltime)/(double)1000000000)%(double)60);
	    Gui.timerlabel.setText(String.format("%02d:%02.0f", minutes, seconds));
	}
	totaltime += elapsed;
    }    
}
