import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MousePanel extends JPanel implements MouseListener, MouseMotionListener{

	final static float dash1[] = {10.0f};
	final static BasicStroke dashed = new BasicStroke(1.0f, 
	                                                      BasicStroke.CAP_BUTT, 
	                                                      BasicStroke.JOIN_MITER, 
	                                                      10.0f, dash1, 0.0f);
	private static int counter = 0,
					   totalUndos = 19; //totalUndos + 1 is actual number of undos
	private static BufferedImage gridArray[] = new BufferedImage[totalUndos + 1];
    public static MousePanel inst;
    private static BufferedImage grid,
    							 gridDrag;
    private static Graphics2D gc;
    private static int startX,
   					   startY,
					   endX,
    				   endY,
    				   dragStartX,
    				   dragStartY,
      				   flag = 0,
    				   width = 0, 
	                   height = 0,
					   changeX,
					   changeY,
					   w,
					   h,
					   nowX,
					   nowY;
	private static boolean change = false,
						   first = true,
						   first1 = true,
						   undoFirst = true,
						   resize = false,
						   mouseDrg = false;

	//mouse listener for panel
    MousePanel()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
        //setSize(1000, 1000);
        Dimension d = new Dimension(750, 600);
        setPreferredSize(d);
    }    
    
    public static MousePanel getInstance()
    {
        if(inst == null)
            inst =  new MousePanel();
         return inst;
    }
    
    public static void changeFlag(int i)
    {
    	flag = i;
    }
    
    public static void changeResize(boolean b)
    {
    	resize = b;
    }
    
    //draw empty rectangle
    public void drawRect()
	{
    	if (startY > endY && startX > endX)
			gc.drawRect(startX - width, startY - height, width, height);
		else if(startX > endX)
			gc.drawRect(startX - width, startY, width, height);
		else if (startY > endY)
			gc.drawRect(startX, startY - height, width, height);
		else
			gc.drawRect(startX, startY, width, height);
		repaint();
	}
    
    //draw filled rectangle and draw empty border around it
    public void fillRect()
    {
    	gc.setColor(SliderPanel.getInstance().getStrokeColor());
    	if (startY > endY && startX > endX)
			gc.fillRect(startX - width, startY - height, width, height);
		else if(startX > endX)
			gc.fillRect(startX - width, startY, width, height);
		else if (startY > endY)
			gc.fillRect(startX, startY - height, width, height);
		else
			gc.fillRect(startX, startY, width, height);
		repaint();
		repaint();
		gc.setStroke(new BasicStroke(SliderPanel.getStrokeThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		gc.setColor(SliderPanel.getInstance().getColor());
		if (startY > endY && startX > endX)
			gc.drawRect(startX - width, startY - height, width, height);
		else if(startX > endX)
			gc.drawRect(startX - width, startY, width, height);
		else if (startY > endY)
			gc.drawRect(startX, startY - height, width, height);
		else
			gc.drawRect(startX, startY, width, height);
	}

    //draw empty oval
	public void drawOval()
	{
		//gc.setColor(SliderPanel.getInstance().getStrokeColor());
		if (startY > endY && startX > endX)
			gc.drawOval(startX - width, startY - height, width, height);
		else if (startY > endY)
			gc.drawOval(startX, startY - height, width, height);		
		else if (startX > endX)
			gc.drawOval(startX - width, startY, width, height);
		else
			gc.drawOval(startX, startY, width, height);
		repaint();
	}
	
	//draw filled oval and draw empty border around it
	public void fillOval()
	{		
		gc.setColor(SliderPanel.getInstance().getStrokeColor());
		if (startY > endY && startX > endX)
			gc.fillOval(startX - width, startY - height, width, height);
		else if(startX > endX)
			gc.fillOval(startX - width, startY, width, height);
		else if (startY > endY)
			gc.fillOval(startX, startY - height, width, height);
		else
			gc.fillOval(startX, startY, width, height);
		repaint();
		gc.setStroke(new BasicStroke(SliderPanel.getStrokeThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		gc.setColor(SliderPanel.getInstance().getColor());
		if (startY > endY && startX > endX)
			gc.drawOval(startX - width, startY - height, width, height);
		else if(startX > endX)
			gc.drawOval(startX - width, startY, width, height);
		else if (startY > endY)
			gc.drawOval(startX, startY - height, width, height);
		else
			gc.drawOval(startX, startY, width, height);
		repaint();
	}
	
	//draw straight line
	public void drawLine()
	{
		gc.drawLine(startX, startY, endX, endY);
		repaint();
	}
	
	//freedraw
	public void freeDraw()
	{
		gc.drawLine(startX, startY, endX, endY);
		repaint();
	}
	
	//dotted line represents bounded box or oval when drawing depending on flag
	public void dottedLine()
	{		
		System.out.println("dottedLine");
		BufferedImage temp =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    	Graphics2D gd = temp.createGraphics();
        gd.drawImage(grid, null, 0, 0);
        gd.setColor(Color.black);
		gd.setStroke (dashed);
		if(flag == 1 || flag == 5){
			if( nowY < dragStartY && nowX < dragStartX )
				gd.drawRect(nowX, nowY, dragStartX - nowX, dragStartY - nowY);
			else if(nowY < dragStartY)
				gd.drawRect(dragStartX, nowY, nowX - dragStartX, dragStartY - nowY);
			else if(nowX < dragStartX)
				gd.drawRect(nowX, dragStartY, dragStartX - nowX, nowY - dragStartY);
			else
				gd.drawRect(dragStartX, dragStartY, nowX - dragStartX, nowY - dragStartY );
		}else if(flag == 3){
                                        gd.drawLine(dragStartX, dragStartY, nowX, nowY);
                                    }	
                                     else if(flag == 2 || flag == 6){
                                         if( nowY < dragStartY && nowX < dragStartX )
				gd.drawOval(nowX, nowY, dragStartX - nowX, dragStartY - nowY);
			else if(nowY < dragStartY)
				gd.drawOval(dragStartX, nowY, nowX - dragStartX, dragStartY - nowY);
			else if(nowX < dragStartX)
				gd.drawOval(nowX, dragStartY, dragStartX - nowX, nowY - dragStartY);
			else
				gd.drawOval(dragStartX, dragStartY, nowX - dragStartX, nowY - dragStartY );
                                     }
		
    	gridDrag = temp;
    	repaint();
	}
	
	//paints over with white
	public void erase()
	{
		gc.setColor(Color.white);
		gc.drawLine(startX, startY, endX, endY);
		repaint();
	}
	
	//clears image
	public static void clear()
	{
		grid = null;
		getInstance().repaint();
	}
	
	public void setBGColor()
	{
		gc.fillRect(0, 0, w, h );
		repaint();
	}
	
	//Method works, but resizing blurs image.
	public void resize()
	{
		System.out.println("resize");
		w = this.getWidth();
    	h = this.getHeight();
                  
       	BufferedImage temp =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
       	gc = temp.createGraphics();
    
       	gc.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gc.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
    	gc.drawImage(grid, 0, 0, w, h, null);
                  
    	grid = temp;
    	repaint();
    }
	
	//click to place text in any location
	public void addText()
	{
		String inString;
		inString = JOptionPane.showInputDialog("Enter text.");
		
		gc.setFont(new Font("sansserif", Font.PLAIN, SliderPanel.textSize)); 
		System.out.println("adding text");
		if (inString != null){
			gc.drawString(inString, startX, startY);
		}
    	repaint();
    }
	
	//undo paint
	public static void undoLast()
	{
		if(counter == totalUndos && gridArray[counter] != null && undoFirst == true){
			BufferedImage temp =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    	gc = temp.createGraphics();
	    	if(counter >= 1)
	    		gc.drawImage(gridArray[counter - 1], 0, 0, w, h, null);
	    	else
	    		gc.drawImage(gridArray[counter], 0, 0, w, h, null);
	    	grid = temp;
	       	getInstance().repaint();
	    	undoFirst = false;
		}else if(counter != 0){
			counter--;
			first1 = true;
		BufferedImage temp =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    	gc = temp.createGraphics();
    	if(counter >= 1)
    		gc.drawImage(gridArray[counter - 1], 0, 0, w, h, null);
    	else
    		gc.drawImage(gridArray[counter], 0, 0, w, h, null);
    	grid = temp;
    	getInstance().repaint();
		}
	}
		
	//redo paint
	public static void redo()
	{
		System.out.println("flag = " + flag);
		System.out.println("redo "+ counter);
		if(counter != totalUndos && gridArray[counter + 1] != null){
			System.out.println("redo "+counter);
			counter++;
			BufferedImage temp =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			gc = temp.createGraphics();
			gc.drawImage(gridArray[counter - 1], 0, 0, w, h, null);
    		grid = temp;
    		getInstance().repaint();
		}
		else if(gridArray[counter + 1] == null){
			BufferedImage temp =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			gc = temp.createGraphics();
			gc.drawImage(gridArray[counter], 0, 0, w, h, null);
    		grid = temp;
    		getInstance().repaint();
		}
    }
	
	//switch for flags called in drawing
	public void drawing()
	{	
		gc.setStroke(new BasicStroke(SliderPanel.getStrokeThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		gc.setColor(SliderPanel.getColor());
		switch(flag){
		    case 1: drawRect();   break;
		    case 2: drawOval();   break;
		    case 3: drawLine();   break;
		    //case 4: 
		    case 5: fillRect();   break;
		    case 6: fillOval();   break;
		    case 7: freeDraw();   break;
		    case 8: erase();      break;
		    case 9: resize();     break;
		    case 10: addText();	  break;
			}	
		undoFirst = true;
	}
	
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        if (grid == null){
        	w = this.getWidth();
        	h = this.getHeight();
          	grid =  new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
          	gc=grid.createGraphics();
        	gc.setColor(Color.white);
        	gc.fillRect(0, 0, w, h);  
        	storeGrid(grid);        	
        }
        if (resize == true)
        {resize();
        resize = false;}
        
        if(flag != 7 && flag != 8 && flag != 0 && flag!=4 && mouseDrg == false)
            storeGrid(grid);
        
        if(mouseDrg == true){
        	g2.drawImage(gridDrag, null, 0, 0);
            gridDrag = null;
        }
        else 
        	g2.drawImage(grid, null, 0, 0);
    }
                      
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e)
    {
    	startX = e.getX();
    	startY = e.getY();  
    	dragStartX = startX;
    	dragStartY = startY;
    }
    
    public void mouseReleased(MouseEvent e)
    {    	
    	endX = e.getX();
    	endY = e.getY();
    	first = true;
    	
    	if(startY > endY)
    		height = startY - endY;
    	else
    		height = endY - startY;
    	if(startX > endX)
    		width = startX - endX;
    	else
    		width = endX - startX;
    	
    	mouseDrg = false;
    	if(flag == 7 || flag == 8)
    		storeGrid(grid);
       	drawing();
    }
    
    public void mouseDragged(MouseEvent e)
    {
    	//mouseDrg = true;
        nowX = e.getX();
        nowY = e.getY();
    	
    	
    	if(flag == 7 || flag == 8){
    	
    		if(first == true){
    			startX = dragStartX;
    			startY = dragStartY;
    		
    			endX = e.getX();
    			endY = e.getY();
    			changeX = endX;
    			changeY = endY;
    		
    			first = false;
    			change = true;
    		}
    		else if(change == false){
    			startX = endX;
    			startY = endY;
    			endX = e.getX();
    			endY = e.getY();
    			changeX = endX;
    			changeY = endY;
    			change = true;
    		}
    		else if(change == true){
    			startX = changeX;
    			startY = changeY;
    			endX = e.getX();
    			endY = e.getY();
    			change = false;
    		}
    		
    		drawing();
    	}
    		if(flag < 7 && flag != 0){
    		mouseDrg = true;
    		dottedLine();
    		}
    	
    }
   
    public void mouseMoved(MouseEvent e){}
    
    //this method might be able to be more robust. I don't know. 
    //If a user inputs a file extension, it gets saved as part of the name
    public static void saveImage() {
    	System.out.println("saveImage");
    	
                    JFileChooser fileChooser = new JFileChooser();
                    int returnVal = fileChooser.showSaveDialog(null);
                    

     if(returnVal == JFileChooser.APPROVE_OPTION){
         
     
        try{
        File outputfile = new File(fileChooser.getSelectedFile() + ".png");
        ImageIO.write(grid, "png", outputfile);
        }
        catch (IOException e) {
        System.out.println("Image could not be read");
        System.exit(1);
        }
     }
    }
    
    public void storeGrid(BufferedImage temp)
    {
     	BufferedImage temp1 =  new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd;
        gd = temp1.createGraphics();
    	gd.drawImage(grid, 0, 0, w, h, null);	
    	    	
    	if(counter < totalUndos){
    		gridArray[counter] = temp1;
    		counter++;
    	}else if(counter == totalUndos && gridArray[totalUndos]!=null && first1 == true){
    		gridArray[counter] = temp1;
    		first1 = false;
    	}else if(counter == totalUndos && gridArray[totalUndos]!=null){
    		System.arraycopy(gridArray, 1, gridArray, 0, totalUndos);
    		gridArray[counter] = temp1;
    	}else if(counter == totalUndos && gridArray[totalUndos]==null)
    		gridArray[counter] = temp1;
    		   	
    }
}