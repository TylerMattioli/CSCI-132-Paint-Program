
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ButtonPanel extends JPanel implements ActionListener {

    public static ButtonPanel inst;
    private JButton rectangleButton,
    				fillRectangleButton,
    				ovalButton,
     				fillOvalButton,
     				lineButton,
     				clearButton,
    				freeDrawButton,
    				eraserButton,
    				undoButton,
    				redoButton,
    				textButton;
    private static int buttonWidth = 35,
    				   buttonHeight = 30,
    			       imgWidth = 30,
    			       imgHeight = 25,
    			       startX = 2, 
    			       startY = 2,
    			       imgType = 2;
    private static Color buttonColor;
     
    ButtonPanel()
    {
    	GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
    	setBackground(Color.DARK_GRAY);
    	setBorder(new LineBorder(Color.black, 1));
    	c.fill = GridBagConstraints.BOTH;
    	c.anchor = GridBagConstraints.NORTH;
      	
    	rectangleButton = createImageButton(buttonWidth, buttonHeight, imgWidth, imgHeight, startX, startY, imgType, "drawRect");
    	
    	c.gridwidth = GridBagConstraints.RELATIVE;
    	//c.gridx = 0;
    	//c.gridy = 0;
      	
        gridbag.setConstraints(rectangleButton, c);
    	add(rectangleButton);
    	
    	fillRectangleButton = createImageButton(buttonWidth, buttonHeight, imgWidth, imgHeight, startX, startY, imgType, "fillRect");
    	c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(fillRectangleButton, c);
    	add(fillRectangleButton);
    	    	
    	ovalButton = createImageButton(buttonWidth, buttonHeight, imgWidth, imgHeight, startX, startY, imgType, "drawOval");
    	c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(ovalButton, c);
    	add(ovalButton);

    	fillOvalButton = createImageButton(buttonWidth, buttonHeight, imgWidth, imgHeight, startX, startY, imgType, "fillOval");
    	c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(fillOvalButton, c);
    	add(fillOvalButton);
    	
    	lineButton = createImageButton(buttonWidth, buttonHeight, imgWidth, imgHeight, startX, startY, imgType, "drawLine");
    	c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(lineButton, c);
    	add(lineButton);
    	
    	freeDrawButton = createTextButton("freeDraw");
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(freeDrawButton, c);
        add(freeDrawButton);
        
        textButton = createTextButton("text");
        c.gridwidth =GridBagConstraints.REMAINDER;
        gridbag.setConstraints(textButton, c);
        add(textButton);
        
        eraserButton = createTextButton("eraser");
        eraserButton.setToolTipText("Keep your hands off my eraserButton");
        //eraserButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(eraserButton, c);
        add(eraserButton);
        
        clearButton = createTextButton("clear");
        //clearButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(clearButton, c);
        add(clearButton);
        
        undoButton = createTextButton("undo") ;
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(undoButton, c);
        add(undoButton);
        
        redoButton = createTextButton("redo");
        c.gridwidth =GridBagConstraints.REMAINDER;
        gridbag.setConstraints(redoButton, c);
        add(redoButton);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(SliderPanel.getInstance(), c);
        add(SliderPanel.getInstance());        
    }
    
    public JButton createImageButton(int buttonWidth, int buttonHeight,int imgWidth, int imgHeight, int startX,
    									int startY, int imgType, String buttonImage)
    {
        	
    	BufferedImage buf = new BufferedImage(buttonWidth, buttonHeight, imgType);
    	Graphics2D gc = buf.createGraphics();
    	 gc.setColor(SliderPanel.getColor());
    	ImageIcon ButtonIcon = new ImageIcon(buf);
    	
    	if(buttonImage.equals("drawRect"))
    		getImageIcon(imgWidth, imgHeight, startX, startY,"drawRect");
    	if(buttonImage.equals("fillRect"))
    		getImageIcon(imgWidth, imgHeight, startX, startY, "fillRect");
    	if(buttonImage.equals("drawOval"))
    		getImageIcon(imgWidth, imgHeight, startX, startY, "drawOval");
    	if(buttonImage.equals("fillOval"))
    		getImageIcon(imgWidth, imgHeight, startX, startY, "fillOval");
    	if(buttonImage.equals("drawLine"))
    		getImageIcon(imgWidth, imgHeight, startX, startY, "drawLine");
            	
    	JButton button = new JButton(ButtonIcon);
    	button.setActionCommand(buttonImage);
    	//button.setFocusPainted(true);
        button.addActionListener(this);
    	button.setAlignmentX(CENTER_ALIGNMENT);
        
        return button;
    }
    public JButton createTextButton(String buttonText)
    {
    	 JButton button = new JButton(buttonText);
     	 button.setActionCommand(buttonText);
     	 //button.setFocusPainted(true);
         button.addActionListener(this);
     	 button.setAlignmentX(CENTER_ALIGNMENT);
         
         return button;
    }
    
    public static ButtonPanel getInstance()
     {
        if(inst == null){
           inst =  new ButtonPanel();
        }
        return inst;
     }
                  
     public void actionPerformed(ActionEvent ae)
     {
         if(ae.getActionCommand().equals("drawRect"))
        	 MousePanel.changeFlag(1);
         else if(ae.getActionCommand().equals("drawOval"))
        	 MousePanel.changeFlag(2);
         else if(ae.getActionCommand().equals("drawLine"))
        	 MousePanel.changeFlag(3);
         else if(ae.getActionCommand().equals("clear")){
        	 MousePanel.changeFlag(4);
        	 MousePanel.clear();}
         else if(ae.getActionCommand().equals("fillRect"))
        	 MousePanel.changeFlag(5);
         else if(ae.getActionCommand().equals("fillOval"))
        	 MousePanel.changeFlag(6);
         else if(ae.getActionCommand().equals("freeDraw"))
        	 MousePanel.changeFlag(7);
         else if(ae.getActionCommand().equals("eraser"))
        	 MousePanel.changeFlag(8);
         else if(ae.getActionCommand().equals("undo")){
        	 MousePanel.undoLast();
        	 MousePanel.changeFlag(0);
         }else if(ae.getActionCommand().equals("redo")){
        	 MousePanel.redo();
        	 MousePanel.changeFlag(0);
         }else if(ae.getActionCommand().equals("text")){
        	 MousePanel.changeFlag(10);
         }else System.out.println("you messed up bro");
     }
     
     
     public void paintComponent(Graphics g)
     {
    	super.paintComponent(g);
       
     	buttonColor =  SliderPanel.getColor();
     	rectangleButton.setIcon(getImageIcon(imgWidth, imgHeight, startX, startY, "drawRect"));
     	fillRectangleButton.setIcon(getImageIcon(imgWidth, imgHeight, startX, startY, "fillRect"));
       	ovalButton.setIcon(getImageIcon(imgWidth, imgHeight, startX, startY, "drawOval"));
       	fillOvalButton.setIcon(getImageIcon(imgWidth, imgHeight, startX, startY, "fillOval"));
       	lineButton.setIcon(getImageIcon(imgWidth, imgHeight, startX, startY, "drawLine"));
   	 	freeDrawButton.setForeground(buttonColor);
    	freeDrawButton.repaint();
    	textButton.setForeground(buttonColor);
    	textButton.repaint();
     }
     
     //draw on buttons
     public ImageIcon getImageIcon(int imgWidth, int imgHeight, int startX, int startY, String buttonImage)
     {
    	 BufferedImage buf = new BufferedImage(buttonWidth, buttonHeight, imgType);
     	 Graphics2D gd = buf.createGraphics();
     	 gd.setColor(buttonColor);
     	 BasicStroke st = new BasicStroke(SliderPanel.getStrokeThickness()/4);
     	
     	 if(buttonImage.equals("drawRect")){
     		 gd.setStroke(st);
    		 gd.drawRect(startX, startY , imgWidth, imgHeight);
     	 }if(buttonImage.equals("fillRect"))
    		gd.fillRect(startX, startY , imgWidth, imgHeight);
     	 if(buttonImage.equals("drawOval")){
     		 gd.setStroke(st);
    		 gd.drawOval(startX, startY , imgWidth, imgHeight);
     	 }if(buttonImage.equals("fillOval"))
    		gd.fillOval(startX, startY , imgWidth, imgHeight);
    	 if(buttonImage.equals("drawLine")){
    		 gd.setStroke(st);
    		 gd.drawLine(buttonWidth - imgWidth, buttonHeight/2 ,(buttonWidth - imgWidth) + imgWidth , buttonHeight/2);
    	 }
         ImageIcon buttonIcon = new ImageIcon(buf);
      	 return buttonIcon;      
     }
}