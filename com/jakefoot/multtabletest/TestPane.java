package com.jakefoot.multtabletest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestPane extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 7692304434779827184L;
    private int[][] scoretable = new int[Gui.max+1][Gui.max+1];
    private int numright = 0;
    private int j;
    private int focrow;
    private int foccol;
    private int gvnans;
    public long tottime = 0;
    private float score;
    private String fieldtxt;
    private String highlighttxt;    
    private static final Color highlight = new Color(192,192,192);
    private boolean error = false;
    private static JTextField[][] fieldarray = new JTextField[Gui.max+1][Gui.max+1];
    public static JLabel timerlabel;
    public static Dimension celldimension = new Dimension(70, 30);
    public static Font headerfont = new Font (Font.SANS_SERIF, Font.BOLD, 20);
    public static Font tablefont = new Font (Font.SANS_SERIF, Font.BOLD, 20);
    Thread timethread;
        
    
    //constructor
    public TestPane()
    {
	super();
	setLayout(new GridLayout(Gui.max+1, Gui.max+1, 2, 2));
	setBackground(Color.BLACK);
	createGrid(Gui.max);
	//enableGrid();
	fillHeaders(Gui.max);
	addListeners(Gui.max);
	initializeScore(Gui.max);
	Gui.showScore(false);
    }   

    //METHODS
    //creates empty grid
    private void createGrid(int maximum)
    {
	for (int i = 0; i <= Gui.max; ++i)
	{
	   fieldarray[i][j] = new JTextField();
	   fieldarray[i][j].setFont(tablefont);
	   fieldarray[i][j].setPreferredSize(celldimension);;
	   fieldarray[i][j].setBorder(null);
	   //fieldarray[i][j].setEditable(false);
	   fieldarray[i][j].setBackground(Color.WHITE);
	   fieldarray[i][j].setHorizontalAlignment(JTextField.CENTER);
	   add(fieldarray[i][j]);	   
	   
	   for (int j = 1; j <= Gui.max; j++)
	   {
	       fieldarray[i][j] = new JTextField();
	       fieldarray[i][j].setFont(tablefont);
	       fieldarray[i][j].setPreferredSize(celldimension);
	       fieldarray[i][j].setBorder(null);
	       //fieldarray[i][j].setEditable(false);
	       fieldarray[i][j].setBackground(Color.WHITE);
	       fieldarray[i][j].setHorizontalAlignment(JTextField.CENTER);	       
	       add(fieldarray[i][j]);		   
	   }
	}
    }    
    
    //adds action listeners to grid cells
    private void addListeners(int maximum)
    {
	j = 1;
	for (int i = 1; i <= Gui.max; ++i)
	{
	   for (int j = 1; j <= Gui.max; j++)
	   {
	       fieldarray[i][j].addActionListener(new FieldHandlerClass(i, j));
	       fieldarray[i][j].addMouseListener(new MouseHandlerClass(i, j));
	       fieldarray[i][j].addFocusListener(new FocusHandlerClass(i,j));
	   }
	}
    }    
    
    //adds row and column headers and disables these cells
    private void fillHeaders(int maximum)
    {
	fieldarray[0][0].setText(null);
	fieldarray[0][0].setEnabled(false);
	fieldarray[0][0].setBackground(Color.WHITE);
	fieldarray[0][0].setBorder(null);
	for (int i = 1; i <= Gui.max; ++i)
	{
	   fieldarray[i][0].setText("" + i);
	   fieldarray[i][0].setEnabled(false);
	   fieldarray[i][0].setBackground(Color.WHITE);
	   fieldarray[i][0].setDisabledTextColor(Color.BLACK);
	}
	for (int i = 1; i <= Gui.max; ++i)
	{
	   fieldarray[0][i].setText("" + i);
	   fieldarray[0][i].setEnabled(false);
	   fieldarray[0][i].setBackground(Color.WHITE);
	   fieldarray[0][i].setDisabledTextColor(Color.BLACK);
	}
    }
    
    //enables grid cells allowing answer entry
    public void enableGrid()
    {
	for (int i = 1; i <= Gui.max; ++i)
	{
	   for (int j = 1; j <= Gui.max; j++)
	   {
	       fieldarray[i][j].setEditable(true);	       
	   }
	}
    }
    
    //disables grid preventing answer entry
    public void disableGrid()
    {
	for (int i = 1; i <= Gui.max; ++i)
	{
	   for (int j = 1; j <= Gui.max; j++)
	   {
	       fieldarray[i][j].setEditable(false);	       
	   }
	}
    }

    //initializes the score array (scoretable) to all zeros (1 indicates correct answer in corresponding cell)
    private void initializeScore(int maximum)
    {
	numright = 0;
	j = 0;
	for (int i = 0; i <= Gui.max; i++)
	{
	    scoretable[i][j] = 0;
	   
	   for (int j = 0; j <= Gui.max; j++)
	   {
	       scoretable[i][j] = 0;   
	   }
	}
    }    
    
    //evaluates answer in given cell
    public void evaluateAnswers()
    {	
	for (int r = 1; r < scoretable.length; r++)
	{
		for (int c = 1; c < scoretable[r].length; c++)
		{
		    int answer = r * c;	
		    try
		    {
		    gvnans = Integer.valueOf(fieldarray[r][c].getText());
		    }
		    catch(Exception e)
		    {
			gvnans = 0;
		    }
		    if (gvnans == answer)
		    {
			fieldarray[r][c].setForeground(Color.GREEN);
			scoretable[r][c] = 1;
		    }
		    else
		    {
			fieldarray[r][c].setForeground(Color.RED);
			fieldarray[r][c].setText(String.format("%d (%d)", gvnans, answer));
			scoretable[r][c] = 0;
		    }
		}
	}
    }
    
    //reads scoretable, calculates and displays score disabling grid and stopping timer if grid is complete
    public void updateScore()
    {
	numright = 0;
	for (int x = 0; x < scoretable.length; x++)
	{
		for (int y = 0; y < scoretable[x].length; y++)
		{
			numright += scoretable[x][y];
		}
	}	
	score = (float)numright/((float)Gui.max*(float)Gui.max)*100;
	Gui.setScore(score);	
    }    


    //EVENT HANDLER CLASSES
    private class FieldHandlerClass implements ActionListener
    {
	private int row;
	private int col;
	public FieldHandlerClass(int i, int j)
	{
	    row = i;
	    col = j;	    
	}
	@Override
	public void actionPerformed(ActionEvent event)
	{	    
	    fieldarray[row][col].transferFocus();	    
	}	
    }
    
    private class MouseHandlerClass extends MouseAdapter
    {
	private int row;
	private int col;
	
	
	public MouseHandlerClass(int i, int j)
	{
	    row = i;
	    col = j;	    
	}	
	@Override
	public void mouseEntered(MouseEvent event)
	{
	    highlighttxt = fieldarray[row][col].getText();   
	    if (!fieldarray[row][col].hasFocus())
	    {		
		fieldarray[row][col].setBackground(highlight);
		if (highlighttxt.isEmpty())
		{
		    fieldarray[row][col].setForeground(Color.BLACK);
		    fieldarray[row][col].setText(String.format("%dx%d", row, col));
		}
	    }	    
		if (col != foccol)
		    fieldarray[0][col].setBackground(highlight);
		if (row != focrow)
		    fieldarray[row][0].setBackground(highlight);	    
	}	
	@Override
	public void mouseExited(MouseEvent event)
	{
	    highlighttxt = fieldarray[row][col].getText();
	    if (!fieldarray[row][col].hasFocus())
		{
		fieldarray[row][col].setBackground(Color.WHITE);
		if (highlighttxt.contains("x"))
		    {
			fieldarray[row][col].setText("");
		    }
		if (col != foccol)
		    fieldarray[0][col].setBackground(Color.WHITE);
		if (row != focrow)
		    fieldarray[row][0].setBackground(Color.WHITE);
		}
	}	
    }
    
    private class FocusHandlerClass implements FocusListener
    {
	private int row;
	private int col;
	public FocusHandlerClass(int i, int j)
	{
	    row = i;
	    col = j;
	}
	@Override
	public void focusGained(FocusEvent event)
	{
	    fieldtxt = fieldarray[row][col].getText();
	    focrow = row;
	    foccol = col;
	    if (fieldtxt.isEmpty())
	    {
		fieldarray[row][col].setText(String.format("%dx%d", row, col));
	    }	    
	    fieldarray[row][col].setBackground(Color.YELLOW);
	    fieldarray[0][col].setBackground(Color.YELLOW);
	    fieldarray[row][0].setBackground(Color.YELLOW);
	    fieldarray[row][col].selectAll();
	    fieldtxt = "";
	}
	@Override
	public void focusLost(FocusEvent event)
	{	    
	    fieldtxt = fieldarray[row][col].getText();
	    fieldarray[row][col].setBackground(Color.WHITE);
	    fieldarray[0][col].setBackground(Color.WHITE);
	    fieldarray[row][0].setBackground(Color.WHITE);
	    
	    if (fieldtxt.contains("x"))
	    {
		fieldarray[row][col].setText("");
		fieldtxt = "";
	    }
	    
	    if (fieldarray[row][col].isEditable())
	    {
	    if (!fieldtxt.isEmpty())
	    {
	    try
	    {
		gvnans = Integer.valueOf(fieldtxt);		
	    }
	    catch (Exception e)
	    {
		error = true;		
	    }
	    if (error == true)
	    {
		JOptionPane.showMessageDialog(null, "Invalid entry. Please enter an integer value.", "Error", JOptionPane.ERROR_MESSAGE);
		fieldarray[row][col].requestFocus();
		error = false;
	    }	    
	    }
	    }
	}
    }
	
}