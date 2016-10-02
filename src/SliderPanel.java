import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;


import java.util.Hashtable;
 
/*
 * 
 */
public class SliderPanel extends JPanel// implements ChangeListener
{
	public static SliderPanel inst;
    static final int min = 0;
    static final int max = 255;
    static final int initial = 0;    //initial frames per second
    private static int redValue = 0;
    private static int greenValue = 0;
    private static int blueValue = 0;
    private static Color pickColor = Color.BLACK;
    private static Color pickColor2 = Color.WHITE;
    private static int intlStrokeValue = 3;
    private static float strokeValue = (float) intlStrokeValue;
    static int textSize = 25;
    private static Color newColor;
    private static Color strokeColor;
    //private static boolean first = true;
            
    public SliderPanel() {
        //super(new BoxLayout());
 
    	//background color and box layout
        setBackground(Color.DARK_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        int sliderWidth = 130;
        int sliderHeight =50;
        Dimension d = new Dimension(sliderWidth, sliderHeight);
        
        //panel to hold fill and outline colors
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.DARK_GRAY);
        colorPanel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        colorPanel.setPreferredSize(new Dimension(165,110));
        
        //fill color button
        JButton fillColorChooser = new JButton();
        fillColorChooser.setBackground(Color.BLACK);
        fillColorChooser.setPreferredSize(new Dimension(50,50));
        fillColorChooser.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(
                                                SliderPanel.this,
                                                "Choose Background Color",
                                                Color.WHITE);
            if (newColor != null) {
            	pickColor = newColor;
            	ButtonPanel.getInstance().repaint();
            	fillColorChooser.setBackground(newColor);
            }
        }   
        });
        
        //outline color button
        JButton outlineColorChooser = new JButton();
        outlineColorChooser.setBackground(Color.WHITE);
        outlineColorChooser.setPreferredSize(new Dimension(50,50));
        outlineColorChooser.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(
                                                SliderPanel.this,
                                                "Choose Outline Color",
                                                Color.WHITE);
            if (newColor != null) {
            	pickColor2 = newColor;
            	ButtonPanel.getInstance().repaint();
            	outlineColorChooser.setBackground(newColor);
            }
        }   
        });
     
        //slide for stroke thickness from 1 to 25
        int strkMin = 1,
            strkMax = 25;
        JSlider strokeSlider = new JSlider(JSlider.HORIZONTAL, strkMin, strkMax, (int) intlStrokeValue);
        strokeSlider.setPreferredSize(new Dimension(165, 48));
        strokeSlider.setMajorTickSpacing(1);
        strokeSlider.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> strokeLabel = new Hashtable<Integer, JLabel>();
        strokeLabel.put(strkMin, new JLabel("1") );
        strokeLabel.put(new Integer( strkMax ), new JLabel("25") );
        strokeSlider.setLabelTable(strokeLabel);
        strokeSlider.setPaintLabels(true);
        strokeSlider.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent evt) {
            JSlider slider = (JSlider)evt.getSource();
            if (!slider.getValueIsAdjusting()) {
              strokeValue = slider.getValue();
              ButtonPanel.getInstance().repaint();
            }
          }
        }
        );
        
        //slide for text size from 1 to 100
        int sizeMin = 1,
            sizeMax = 100;
        JSlider textSlider = new JSlider(JSlider.HORIZONTAL, sizeMin, sizeMax, (int) 25);
        textSlider.setPreferredSize(new Dimension(165, 48));
        textSlider.setMajorTickSpacing(5);
        textSlider.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> textLabel = new Hashtable<Integer, JLabel>();
        textLabel.put(sizeMin, new JLabel("1") );
        textLabel.put(new Integer( sizeMax ), new JLabel("100") );
        textSlider.setLabelTable(textLabel);
        textSlider.setPaintLabels(true);
        textSlider.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent evt) {
            JSlider slider2 = (JSlider)evt.getSource();
            if (!slider2.getValueIsAdjusting()) {
              textSize = slider2.getValue();
            }
          }
        }
        );
       
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
       
        //just a divider
        JPanel bigLine = new JPanel();
        bigLine.setBackground(Color.DARK_GRAY);
        bigLine.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        bigLine.setPreferredSize(new Dimension(165,80));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(bigLine, c);
        add(bigLine);
        
        //text button
        JButton text = new JButton();
        text.setPreferredSize(new Dimension(165,25));
        text.setForeground(Color.BLACK);
        text.setOpaque(true);
        text.setFont(new Font("sansserif", Font.BOLD, 15));
        text.setEnabled(false);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(text, c);
        add(text);
        
        JLabel textText = new JLabel("        Text Size");
        textText.setForeground(Color.black);
        textText.setFont(new Font("sansserif", Font.BOLD, 15));
        text.add(textText);
        
        //just a divider
        JPanel thinLine2 = new JPanel();
        thinLine2.setBackground(Color.DARK_GRAY);
        thinLine2.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        thinLine2.setPreferredSize(new Dimension(165,2));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(thinLine2, c);
        add(thinLine2);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(textSlider, c);
        add(textSlider); 
        
        //just a divider
        JPanel line2 = new JPanel();
        line2.setBackground(Color.DARK_GRAY);
        line2.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        line2.setPreferredSize(new Dimension(165,8));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(line2, c);
        add(line2);
        
        //stroke button
        JButton stroke = new JButton();
        stroke.setPreferredSize(new Dimension(165,25));
        stroke.setForeground(Color.black);
        stroke.setOpaque(true);
        stroke.setFont(new Font("sansserif", Font.BOLD, 15));
        stroke.setEnabled(false);
        //stroke.setBorder(new LineBorder(Color.black, 2));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(stroke, c);
        add(stroke);
        
        JLabel strokeText = new JLabel(" Stroke Thickness");
        strokeText.setForeground(Color.black);
        strokeText.setFont(new Font("sansserif", Font.BOLD, 15));
        stroke.add(strokeText);
        
        //just a divider
        JPanel thinLine = new JPanel();
        thinLine.setBackground(Color.DARK_GRAY);
        thinLine.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        thinLine.setPreferredSize(new Dimension(165,2));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(thinLine, c);
        add(thinLine);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(strokeSlider, c);
        add(strokeSlider); 
        
        //just a divider
        JPanel line = new JPanel();
        line.setBackground(Color.DARK_GRAY);
        line.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        line.setPreferredSize(new Dimension(165,8));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(line, c);
        add(line);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(colorPanel, c);
        add(colorPanel);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(fillColorChooser, c);
        colorPanel.add(fillColorChooser);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(outlineColorChooser, c);
        colorPanel.add(outlineColorChooser);
    	}
    
    	public void actionPerformed(ActionEvent ae)
    	{
    		System.out.println(ae.getActionCommand());
    	}
    
    	public static SliderPanel getInstance()
    	{
    		if(inst == null){
    			inst =  new SliderPanel();
    		}
    		return inst;
    	}
    	
        public static Color getColor()
        {
        	newColor = pickColor;
        	return newColor;
        }
        
        public static Color getStrokeColor()
        {
        	strokeColor = pickColor2;
        	return strokeColor;
        }
        
        public static float getStrokeThickness()
        {
          	return strokeValue;
        }
    }

        
        