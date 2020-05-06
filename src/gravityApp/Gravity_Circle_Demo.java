package gravityApp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gravity_Circle_Demo {
	public static JFrame frame = new JFrame();
	
	
	//public static JFrame controlFrame = new JFrame();
	
	
	public static int width = 720;
	public static int height = 500;
	
	public static int controlWidth = 200;
	public static int controlHeight = 500;
	
	public static int gravityWidth = 500;
	public static int gravityHeight = 500;
	
	public static GravityPanel gravityPanel = new GravityPanel(gravityWidth,height);
	public static ControlPanel controlPanel = new ControlPanel(controlWidth, controlHeight);
	
	public static void main(String[] args) {
		new Gravity_Circle_Demo().makeFrame();
	}
	
	public void makeFrame() {
		
		
		
		frame.addMouseListener(gravityPanel);
		frame.addKeyListener(gravityPanel);
		
		//System.out.println("hello");
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING));
		gravityPanel.setPreferredSize(new Dimension(gravityWidth,height));
		controlPanel.setPreferredSize(new Dimension(controlWidth,controlHeight));
		frame.getContentPane().setPreferredSize(new Dimension(width, height));
		frame.setVisible(true);
		
		frame.add(gravityPanel);
		
		frame.add(controlPanel);
		
		
		
		
		//System.out.println(gravityPanel.getWidth());
		//gravityPanel.setBackground(new Color(200,200,200));
		
		frame.pack();
		
		
		controlPanel.setSize(new Dimension(controlWidth,height));
		
		//System.out.println(controlPanel.getAlignmentX());
		//System.out.println(controlPanel.getWidth());
		frame.setTitle("GravityApp");
		gravityPanel.start();
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public static int getPanelWidth() {
		
		return width-controlWidth;
		
		
	}
	public static int getHeight() {
		return height;
		
	}
	public static JPanel getControlPanel() {
		return controlPanel;
	}
	
	public static int getMassValueOfControlPanel() {
		return controlPanel.getMassValue();
	}
	
}
