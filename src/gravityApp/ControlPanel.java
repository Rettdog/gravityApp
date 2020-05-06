package gravityApp;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class ControlPanel extends JPanel implements ActionListener {

	public JLabel title = new JLabel("Control Panel");
	public JLabel massLabel = new JLabel("Mass: ");
	public JLabel speedLabel = new JLabel("Max Speed:");
	public JLabel modes = new JLabel("Modes:");
	public JLabel clearLabel = new JLabel("Options:");
	public JSlider massSlider = new JSlider();
	public JSlider maxSpeed = new JSlider();
	public JToggleButton placeMoving = new JToggleButton("Moving Mass");
	public JToggleButton placePin = new JToggleButton("Pinned Mass");
	public JToggleButton pausedToggle = new JToggleButton("Paused");
	public JToggleButton gravityToggle = new JToggleButton("Gravity OFF");
	public JToggleButton arrowField = new JToggleButton("Vector Field OFF");
	public JToggleButton bounceWall = new JToggleButton("Walls ON");
	public JButton clear = new JButton("Clear All");
	public JButton help = new JButton("Help");

	int width;
	int height;
	int minMass = 1;
	int maxMass = 9;
	
	public ControlPanel(int width, int height) {
		massSlider.setSize(150, 20);
		massSlider.setMinimum(minMass);
		massSlider.setMaximum(maxMass);
		massSlider.setValue((maxMass+minMass)/2);
		massLabel.setPreferredSize(new Dimension(200, 30));
		modes.setPreferredSize(new Dimension(200, 30));
		speedLabel.setPreferredSize(new Dimension(200, 30));
		pausedToggle.setSelected(true);
		maxSpeed.setMinimum(0);
		maxSpeed.setMaximum(200);
		clearLabel.setPreferredSize(new Dimension(200,30));
		bounceWall.setSelected(true);
		
		placeMoving.addActionListener(this);
		placePin.addActionListener(this);
		
		pausedToggle.addActionListener(this);
		gravityToggle.addActionListener(this);
		arrowField.addActionListener(this);
		bounceWall.addActionListener(this);
		clear.addActionListener(this);
		help.addActionListener(this);
		

		this.width = width;
		this.height = height;
		this.setSize(width, height);

		this.add(title);
		this.add(massLabel);
		this.add(massSlider);
		this.add(placeMoving);
		this.add(placePin);
		
		this.add(speedLabel);
		this.add(maxSpeed);
		this.add(modes);
		this.add(pausedToggle);
		this.add(gravityToggle);
		this.add(clearLabel);
		this.add(arrowField);
		this.add(bounceWall);
		this.add(clear);
		this.add(help);

	}

	public int getMassValue() {
		return massSlider.getValue();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(placeMoving)) {
			placePin.setSelected(false);
			Gravity_Circle_Demo.gravityPanel.clickState = ClickState.MOVING_MASS;
		}
		if (e.getSource().equals(placePin)) {
			placeMoving.setSelected(false);
			Gravity_Circle_Demo.gravityPanel.clickState = ClickState.PINNED_MASS;
		}
		if(!placeMoving.isSelected()&&!placePin.isSelected()) {
			Gravity_Circle_Demo.gravityPanel.clickState = ClickState.NULL;
		}
		if(e.getSource().equals(pausedToggle)) {
			if(pausedToggle.isSelected()) {
				//when turned on
				pausedToggle.setText("Paused");
				//gravityToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.changeState(State.PAUSE_MODE);
				
			}else {
				
				pausedToggle.setText("Playing");
				//gravityToggle.setSelected(true);
				if(Gravity_Circle_Demo.gravityPanel.gravityON) {
					Gravity_Circle_Demo.gravityPanel.changeState(State.GRAVITY_MODE);
				}else {
					Gravity_Circle_Demo.gravityPanel.changeState(State.DRIFT_MODE);
				}
			}
		}
		if(e.getSource().equals(gravityToggle)) {
			if(gravityToggle.isSelected()) {
				//when turned on
				gravityToggle.setText("Gravity ON");
				System.out.println("ON");
				//pausedToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.gravityON = true;
				
			}else {
				
				gravityToggle.setText("Gravity OFF");
				//pausedToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.gravityON = false;
			}
			
			if(!pausedToggle.isSelected()) {
				if(Gravity_Circle_Demo.gravityPanel.gravityON) {
					Gravity_Circle_Demo.gravityPanel.changeState(State.GRAVITY_MODE);
				}else {
					Gravity_Circle_Demo.gravityPanel.changeState(State.DRIFT_MODE);
				}
			}
		}
		if(e.getSource().equals(clear)) {
			System.out.println("clearing all pins");
			Gravity_Circle_Demo.gravityPanel.manager.clearPins();
		}
		if(e.getSource().equals(arrowField)) {
			if(arrowField.isSelected()) {
				//when turned on
				arrowField.setText("Vector Field ON");
				
				//pausedToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.showVectors = true;
				
			}else {
				
				arrowField.setText("Vector Field OFF");
				//pausedToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.showVectors = false;
			}
		}
		if(e.getSource().equals(bounceWall)) {
			if(bounceWall.isSelected()) {
				//when turned on
				bounceWall.setText("Wall ON");
				
				//pausedToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.wallState = WallBounce.Bounce;
				
			}else {
				
				bounceWall.setText("Wall OFF");
				//pausedToggle.setSelected(false);
				Gravity_Circle_Demo.gravityPanel.wallState = WallBounce.NO_BOUNCE;
			}
		}
		if(e.getSource().equals(massSlider)) {
			massLabel.setText("Mass: "+massSlider.getValue()+" x 10^11");
		}
		if(e.getSource().equals(help)) {
			try {
				
				File tutorial = new File("src/tutorial.txt");
				BufferedReader br = new BufferedReader(new FileReader(tutorial));
				ArrayList<String> strings = new ArrayList<String>();
				String line;
				while((line=br.readLine())!=null) {
					strings.add(line);
				}
				int length = strings.size();
				String[] list = new String[length];
				for(int i = 0;i<length;i++) {
					list[i] = strings.get(i);
				}
				/*
				String out = "";
				String line;
				while((line=br.readLine())!=null) {
					out+=line;
					out+="\n";
				}
				int stringLength = 0;
				ArrayList<String> strings = new ArrayList<String>();
				String[] string = new String[stringLength];
				int index = 0;
				for(int i = 0;i<out.length();i++) {
					String temp = "";
					if(out.substring(i, i+1).equals("*")) {
						
					}else {
						
					}
				}
				*/
				//JLabel text = new JLabel(out);
				//text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 10));
				JScrollPane panel = new JScrollPane(new JList(list));
				panel.setPreferredSize(new Dimension(600,300));
				//JPanel panel = new JPanel();
				//panel.add(new JLabel(out));
				JOptionPane.showMessageDialog(null,panel, "Tutorial/Overview",JOptionPane.PLAIN_MESSAGE);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
