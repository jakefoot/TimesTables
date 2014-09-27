package com.jakefoot.multtabletest;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionsPane extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1111260190210298073L;
    private JLabel titlelabel;
    private JRadioButton practiceradio;
    private JRadioButton testradio;
    private ButtonGroup radios;
    public static JCheckBox timerselect;
    private JCheckBox randomselect;
    private JPanel titlepane;
    private JPanel radiopane;
    private JPanel checkpane;
    
    
    public OptionsPane()
    {
	super();
	setBackground(Color.WHITE);
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setVisible(true);
	
	titlepane = new JPanel();
	titlepane.setLayout(new FlowLayout());
	titlepane.setBackground(Color.WHITE);
	titlelabel = new JLabel("Choose your options then click \"Start\" to continue.");
	titlelabel.setFont(Gui.tablefont);
	titlepane.add(titlelabel);
	add(titlepane);
	
	FlowLayout radiolayout = new FlowLayout();
	radiolayout.setHgap(100);
	radiopane = new JPanel();
	radiopane.setLayout(radiolayout);
	radiopane.setBackground(Color.WHITE);
	practiceradio = new JRadioButton("Practice");
	practiceradio.setFont(Gui.tablefont);
	practiceradio.setBackground(Color.WHITE);
	practiceradio.setSelected(true);
	testradio = new JRadioButton("Test");
	testradio.setFont(Gui.tablefont);
	testradio.setBackground(Color.WHITE);
	testradio.setSelected(true);
	radiopane.add(practiceradio);
	radiopane.add(testradio);
	radios = new ButtonGroup();
	radios.add(practiceradio);
	radios.add(testradio);
	add(radiopane);
	
	checkpane = new JPanel();
	checkpane.setLayout(radiolayout);
	checkpane.setBackground(Color.WHITE);
	timerselect = new JCheckBox("Timer on");
	timerselect.setFont(Gui.tablefont);
	timerselect.setBackground(Color.WHITE);
	timerselect.setToolTipText("Show a timer.");
	randomselect = new JCheckBox("Randomize");
	randomselect.setFont(Gui.tablefont);
	randomselect.setBackground(Color.WHITE);
	randomselect.setToolTipText("A problem will be selected for you at random \nuntil table is completed.");
	randomselect.setEnabled(false);//remove when random option is ready
	checkpane.add(timerselect);
	checkpane.add(randomselect);	
	add(checkpane);
    }
    
    public boolean getTimerSelection()
    {
	return timerselect.isSelected();
    }

    public boolean getRandomSelection()
    {
	return randomselect.isSelected();
    }

    public boolean getPracticeSelection()
    {
	return practiceradio.isSelected();
    }
    
 
}
