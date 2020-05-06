package gravityApp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GravityPanel extends JPanel implements MouseListener, ActionListener, KeyListener{
	public JFrame frame;
	public static State state = State.PAUSE_MODE;
	public static boolean gravityON = false;
	public boolean followMouse;
	public ClickState clickState = ClickState.NULL;
	public State lastState = State.DRIFT_MODE;
	public WallBounce wallState = WallBounce.Bounce;
	Timer timer;
	int width;
	int height;
	public double maxSpeed = 100000;
	public boolean showVectors = false;
	//static MainObject object = new MainObject(250,250, 10, 10);
	Background background;
	ObjectManager manager;
	public GravityPanel(int width, int height) {
		timer = new Timer(1000/60, this);
		this.width = width;
		this.height = height;
		this.setSize(width, height);
		
		frame = Gravity_Circle_Demo.getFrame();
		
		background = new Background(0,0,Gravity_Circle_Demo.getPanelWidth(),Gravity_Circle_Demo.getHeight());
		manager = new ObjectManager();
		followMouse = false;
	}
	
	//public static MainObject getObject() {
		//return object;
	//}
	
	public void changeState(State newState) {
		lastState = state;
		state = newState;
	}
	
	public void start() {
		timer.start();
	}
	
	public void update() {
		//System.out.println(state);
		
		if(showVectors) {
			manager.updateArrows();
		}
		
		switch(state) {
		case FOLLOW_MOUSE:
			updateFollowMouseMode();
			break;
		case GRAVITY_MODE:
			updateGravityMode();
			break;
		case PAUSE_MODE:
			updatePauseMode();
			break;
		case DRIFT_MODE:
			updateDriftMode();
			break;
		default:
			break;
		
		
		}
		Gravity_Circle_Demo.controlPanel.massLabel.setText("Mass: "+Gravity_Circle_Demo.controlPanel.massSlider.getValue()+" x 10^12");
		if(Gravity_Circle_Demo.controlPanel.maxSpeed.getValue()==Gravity_Circle_Demo.controlPanel.maxSpeed.getMaximum()) {
			maxSpeed = 1000000;
			Gravity_Circle_Demo.controlPanel.speedLabel.setText("Max Speed: No Max");
		}else {
		maxSpeed = Gravity_Circle_Demo.controlPanel.maxSpeed.getValue();
		Gravity_Circle_Demo.controlPanel.speedLabel.setText("Max Speed: "+maxSpeed);
		}
		background.update();
	}
	
	private void updateDriftMode() {
		manager.updateMoving();
		manager.checkCollision();
	}

	public void updateFollowMouseMode() {
		
		try {
//			object.x = getPosition().x;
//			object.y = getPosition().y;
		}catch(Exception e) {
			System.out.println("Mouse not found!\nYou most likely clicked off screen");
		}
//		object.update();
		
	}
	
	public void updateGravityMode() {
		manager.updateGravityPins();
		manager.checkCollision();
		manager.updateMoving();
	}
	
	public void updatePauseMode() {
		
		
	}
	
	public void draw(Graphics g) {
		
		/*
		switch(state) {
		case FOLLOW_MOUSE:
			
			break;
		case GRAVITY_MODE:
			
			break;
		case PAUSE_MODE:
			
			break;
		case DRIFT_MODE:
			
			break;
		default:
			break;
		}
		*/
		//g.setColor(Color.BLACK);
		
		background.draw(g);
		if(showVectors) {
			manager.drawArrows(g);
		}
		manager.drawAll(g);
		
		
		
		
		//object.x++;
		frame.pack();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		update();
		repaint();
	}
	
	
	
	public void paintComponent(Graphics g){
		draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int buttonClicked = e.getButton();
//		System.out.println(buttonClicked);
		if(state!=State.FOLLOW_MOUSE&&clickState==ClickState.PINNED_MASS) {
			if(isMouseVisible()) {
				if(buttonClicked == 1) {
					manager.addPin((int)getPosition().getX(), (int)getPosition().getY(), Gravity_Circle_Demo.getMassValueOfControlPanel(), false);
				}
				
			}
		}
		if(state!=State.FOLLOW_MOUSE&&clickState==ClickState.MOVING_MASS) {
			if(isMouseVisible()) {
				if(buttonClicked == 1) {
					manager.addPin((int)getPosition().getX(), (int)getPosition().getY(), Gravity_Circle_Demo.getMassValueOfControlPanel(), true);
				}
				
			}
		}
		if(buttonClicked == 3) {
			manager.removePin((int)getPosition().getX(), (int)getPosition().getY());
		}
	}
	
	public Point getPosition() {
		if(this.getMousePosition() != null) {
		return this.getMousePosition();
		}
		return null;
	}

	public boolean isMouseVisible() {
		return this.getMousePosition()!=null;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void keyTyped(KeyEvent e) {
		int keycode = e.getKeyCode();
		// TODO Auto-generated method stub
		//System.out.println(keycode);
		
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		// TODO Auto-generated method stub
		//System.out.println(keycode);
		switch(keycode) {
		case 32:
			//space bar
			if(state == State.PAUSE_MODE) {
				state = State.GRAVITY_MODE;
			}else {
				state = State.PAUSE_MODE;
			}
			break;
			
		case 70:
			//f
			state = State.FOLLOW_MOUSE;
			break;
		}
		
		
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
