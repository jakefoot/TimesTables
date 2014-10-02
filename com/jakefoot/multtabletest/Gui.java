package com.jakefoot.multtabletest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Gui extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = -4129130611297297752L;
    public static int max = 12;
    public static long tottime = 0;
    private float score;
    private boolean paused = false;
    public static boolean submitted = false;
    private JPanel tablepane;
    private static JLabel scorelabel;
    private JPanel timerpane;
    public static JLabel timerlabel;
    private JPanel messagepane;
    private JLabel messagelabel;
    private JPanel buttonpane;
    private static JLabel headerlabel;
    private static JButton startbutton;
    private static JButton resetbutton;
    private static JButton pausebutton;
    private static JButton menubutton;
    private static JButton submitbutton;
    private static OptionsPane ops;
    public static Dimension celldimension = new Dimension(70, 30);
    public static Font headerfont = new Font (Font.SANS_SERIF, Font.BOLD, 20);
    public static Font tablefont = new Font (Font.SANS_SERIF, Font.BOLD, 20);
    public static Container grid;
    
    private static Timer timer;
    static Thread timethread;
        
    
    //constructor
    public Gui()
    {
	super("Multiplication Table Options");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//define and add panes
	Container contpanel = getContentPane();
	contpanel.setLayout(new BoxLayout(contpanel, BoxLayout.PAGE_AXIS));	
	JPanel headerpane = new JPanel(new FlowLayout(FlowLayout.CENTER));
	headerpane.setBackground(Color.WHITE);	
	contpanel.add(headerpane);
	
	headerlabel = new JLabel("Multiplication Table");
	headerlabel.setFont(headerfont);
	headerpane.add(headerlabel);
	
	tablepane = new JPanel();
	tablepane.setBackground(Color.WHITE);
	ops = new OptionsPane();
	tablepane.add(ops, BorderLayout.CENTER);
	contpanel.add(tablepane);
	
	messagepane = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	messagepane.setBackground(Color.WHITE);
	messagepane.setVisible(false);
	contpanel.add(messagepane);
	
	messagelabel = new JLabel();
	messagelabel.setFont(headerfont);
	messagepane.add(messagelabel, gbc);
	
	JPanel scorepane = new JPanel(new FlowLayout());
	scorepane.setBackground(Color.WHITE);
	contpanel.add(scorepane);
	
	scorelabel = new JLabel();
	scorelabel.setFont(headerfont);
	scorelabel.setForeground(Color.WHITE);
	scorelabel.setText(String.format("%.1f%% Complete", score));
	scorepane.add(scorelabel);
	
	timerpane = new JPanel(new FlowLayout());
	timerpane.setBackground(Color.WHITE);
	contpanel.add(timerpane);
	
	timerlabel = new JLabel();
	timerlabel.setFont(tablefont);
	timerlabel.setForeground(Color.WHITE);
	timerpane.add(timerlabel);
	
	FlowLayout buttonlayout = new FlowLayout();
	buttonlayout.setHgap(20);
	buttonpane = new JPanel(buttonlayout);
	buttonpane.setBackground(Color.WHITE);
	contpanel.add(buttonpane);
	
	startbutton = new JButton("Start");
	startbutton.setFont(headerfont);
	submitbutton = new JButton("Submit");
	submitbutton.setFont(headerfont);
	submitbutton.setEnabled(false);
	submitbutton.setVisible(false);
	pausebutton = new JButton("Pause");
	pausebutton.setFont(headerfont);
	pausebutton.setEnabled(false);
	resetbutton = new JButton("Reset");
	resetbutton.setFont(headerfont);
	resetbutton.setEnabled(false);
	menubutton = new JButton("Menu");
	menubutton.setFont(headerfont);
	menubutton.setEnabled(false);
	buttonpane.add(startbutton);
	buttonpane.add(submitbutton);
	buttonpane.add(pausebutton);
	buttonpane.add(resetbutton);
	buttonpane.add(menubutton);
	
	addListeners(max);
	pack();
	
    }   

    //adds action listeners to buttons
    private void addListeners(int maximum)
    {
	
	startbutton.addActionListener(new ButtonHandlerClass());
	submitbutton.addActionListener(new ButtonHandlerClass());
	pausebutton.addActionListener(new ButtonHandlerClass());
	resetbutton.addActionListener(new ButtonHandlerClass());
	menubutton.addActionListener(new ButtonHandlerClass());
    }
    
    //creates new timer, removing old one if it exists 
    public static void initializeTimer()
    {
	timer = new Timer();
	timethread = new Thread(timer);
    }
    
    //updates score label
    public static void setScore(float scoreupdate)
    {
	if (ops.getPracticeSelection())
	{
	    scorelabel.setText(String.format("%.1f%% Complete", scoreupdate));
	}
	else
	{
	    scorelabel.setText(String.format("Score: %.1f%%", scoreupdate));
	}
    }
    
    //starts timer
    public static void startTimer()
    {
	initializeTimer();
	timer.setTime(tottime);
	timethread.start();
    }
    
    //pauses timer
    public static void pauseTimer()
    {
	timethread.interrupt();
	try
	{
	    Thread.sleep(5);
	} catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
	tottime = timer.getTime();
    }
    
    //stops timer
    public static void stopTimer()
    {
	timethread.interrupt();
	timer = null;
	timethread = null;
	tottime = 0;
    }
    
    //sets header text
    public static void setHeaderText(String s)
    {
	headerlabel.setText(s);
    }
    
    //sets submitted to true or false
    public static void setSubmitted(boolean tof)
    {
	submitted = tof;
    }
    
    //disables pause button
    public static void enablePause(boolean tof)
    {
	pausebutton.setEnabled(tof);
    }
    
    public static void enableSubmit(boolean tof)
    {
	submitbutton.setEnabled(tof);
    }    
    
    //shows or hides score label
    public static void showScore(boolean tof)
    {
	if (tof)
	    scorelabel.setForeground(Color.BLACK);
	else
	    scorelabel.setForeground(Color.WHITE);
    }    
    
    
    public class ButtonHandlerClass implements ActionListener
    {
	private int result;

	@Override
	public void actionPerformed(ActionEvent event)
	{	    
	    if (event.getSource() == startbutton)
	    {
		initializeTimer();
		setSubmitted(false);
		startbutton.setVisible(false);
		submitbutton.setVisible(true);
		pausebutton.setEnabled(true);
		resetbutton.setEnabled(true);
		menubutton.setEnabled(true);
		tablepane.setVisible(true);
		messagepane.setVisible(false);
		scorelabel.setForeground(Color.BLACK);
		startTimer();
		if (ops.getTimerSelection())
		{
		    timerlabel.setForeground(Color.BLACK);
		}
		else
		{
		    timerlabel.setForeground(Color.WHITE);
		}		
		if (ops.getPracticeSelection() && !ops.getRandomSelection())
		{
		    grid = new PracticePane();
		    ops.setVisible(false);
		    grid.setVisible(true);
		    tablepane.add(grid);
		    pack();
		}
		if (!ops.getPracticeSelection())
		{
		    if (ops.getRandomSelection())
		    {
			grid = new TestPane(true);
		    }
		    else
		    {
			grid = new TestPane(false);
		    }
		    ops.setVisible(false);
		    grid.setVisible(true);
		    tablepane.add(grid);
		    pack();
		}		
		paused = false;
	    }
	    if (event.getSource() == submitbutton)
	    {
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to submit your answers?", "Submit Answers", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION)
		{
		    setSubmitted(true);
		    pauseTimer();		
		    submitbutton.setEnabled(false);
		    pausebutton.setEnabled(false);
		    ((TestPane) grid).disableGrid();
		    ((TestPane) grid).evaluateAnswers();
		    ((TestPane) grid).updateScore();
		    scorelabel.setForeground(Color.BLACK);
		}
	    }
	    if (event.getSource() == pausebutton)
	    {		
		if (!paused)
		{		
		    pauseTimer();
		    messagelabel.setText("Paused... Click Pause to continue.");
		    tablepane.setVisible(false);
		    messagepane.setVisible(true);
		    timer.displayTime(tottime);
		    paused = true;
		}
		else
		{
		    initializeTimer();		    
		    tablepane.setVisible(true);
		    messagepane.setVisible(false);
		    startTimer();
		    paused = false;
		    menubutton.transferFocus();
		}
	    }
	    if (event.getSource() == resetbutton)
	    {		
		if(submitted == false)
		{
		result = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the table?", "Reset", JOptionPane.YES_NO_OPTION);
		}
		if(result == JOptionPane.YES_OPTION || submitted == true)
		{
		    setSubmitted(false);
		    messagepane.setVisible(false);
		    tablepane.setVisible(true);
		    stopTimer();
		    startTimer();
		    tablepane.remove(grid);
		    grid = null;
		    paused = false;
		    pausebutton.setEnabled(true);
		    if (ops.getPracticeSelection())
		    {
			grid = new PracticePane();
			ops.setVisible(false);
			grid.setVisible(true);
			tablepane.add(grid);
			
		    }
		    if (!ops.getPracticeSelection())
		    {			
			if (ops.getRandomSelection())
			    {
				grid = new TestPane(true);
			    }
			    else
			    {
				grid = new TestPane(false);
			    }
			ops.setVisible(false);
			grid.setVisible(true);
			tablepane.add(grid);
			submitbutton.setEnabled(false);
			
		    }
		}
		menubutton.transferFocus();
	    }
	    if (event.getSource() == menubutton)
	    {		
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to the menu?\nAll progress will be lost", "Return To Menu", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION)
		{		
		    messagepane.setVisible(false);
		    tablepane.setVisible(true);
		    ops.setVisible(true);
		    submitbutton.setVisible(false);
		    startbutton.setEnabled(true);
		    startbutton.setVisible(true);		    
		    pausebutton.setEnabled(false);
		    resetbutton.setEnabled(false);
		    menubutton.setEnabled(false);
		    stopTimer();
		    scorelabel.setForeground(Color.WHITE);
		    timerlabel.setForeground(Color.WHITE);
		    tablepane.remove(grid);
		    grid = null;
		    headerlabel.setText("Multiplication Table Options");
		    setScore(0);
		    pack();
		}
	    }
	}		
    }    
}
