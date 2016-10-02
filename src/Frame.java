import java.awt.BorderLayout;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.Popup;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.PopupFactory;
import javax.swing.border.LineBorder;

/**
 *
 * 
 */
public class Frame extends JFrame implements ActionListener, ComponentListener
{
    private static BufferedImage read;
    private static Frame inst;
    final int minWidth = 300;
    final int minHeight = 630;

    Frame( )
    {
    	super("Simple Paint Program");
   	   
    	addComponentListener(this);
        setLocation(200,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.white);    
    
        Container c = getContentPane();
         
        //menus
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenu editMenu = new JMenu("Edit");
            
        //menu items
        JMenuItem newDrawing = new JMenuItem("new");
        JMenuItem save = new JMenuItem("save");
        JMenuItem about = new JMenuItem("about");
        JMenuItem close = new JMenuItem("close");
        JMenuItem undo = new JMenuItem("undo");
        JMenuItem redo = new JMenuItem("redo");
        
        save.addActionListener(this);
        about.addActionListener(this);
        newDrawing.addActionListener(this);
        close.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);
        
        //add to menu
        fileMenu.add(newDrawing);
        fileMenu.add(new JSeparator());
        fileMenu.add(save);
        fileMenu.add(new JSeparator());
        fileMenu.add(close);
        helpMenu.add(about);
        
        editMenu.add(undo);
        editMenu.add(new JSeparator());
        editMenu.add(redo);
        
        //menu bar
        JMenuBar menuBar= new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
  
        JScrollPane scrollPane = new JScrollPane(MousePanel.getInstance());
        c.add(scrollPane, BorderLayout.CENTER);
        c.add(ButtonPanel.getInstance(), BorderLayout.WEST);
        setSize(750, 630);
        setVisible(true);
    }
    
    public static Frame getInstance()
    {    	
    	if(inst == null)
           inst = new Frame();
       
         return inst;
    }
    
    //actions performed 
    public void actionPerformed(ActionEvent ae)
    {    	
    	if(ae.getActionCommand().equals("save"))
    		MousePanel.saveImage();
                else if(ae.getActionCommand().equals("about"))
    		renderSplashFrame(); 
                else if(ae.getActionCommand().equals("undo"))
                                    MousePanel.undoLast();
                else if(ae.getActionCommand().equals("redo"))
                                    MousePanel.redo();
                else if(ae.getActionCommand().equals("new"))
                                    MousePanel.clear();
                else if(ae.getActionCommand().equals("close"))
                                    System.exit(0);
        
    }
   
    //display splash image
	public void renderSplashFrame()
	{
		
		try {
			read = ImageIO.read(new File("small TT light.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon splashIcon = new ImageIcon(read); 
    	JLabel label = new JLabel(splashIcon);
    	
		label.setBorder(new LineBorder(Color.black, 3));
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(MousePanel.getInstance(), label, 450, 275);
		  
		popup.show();
		 ActionListener hider = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		          popup.hide();
		        }
		      };
		      Timer timer = new Timer(7000, hider);
		      timer.start();
       }

	public void componentResized(ComponentEvent ce)
    {
    	System.out.println("component resized");    	
    	MousePanel.changeResize(true);
    	MousePanel.getInstance().repaint();
    	
        int width = getWidth();
        int height = getHeight();
     
        boolean resize = false;
        if (width < minWidth) {
             resize = true;
             width = minWidth;
        }
        if (height < minHeight) {
             resize = true;
             height = minHeight;
        }
        if (resize) {
            setSize(width, height);
      	}
     	
    }
    public void componentHidden(ComponentEvent ce){}
    public void componentShown(ComponentEvent ce){}
    public void componentMoved(ComponentEvent ce){}
   
}
