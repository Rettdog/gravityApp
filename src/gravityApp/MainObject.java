package gravityApp;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class MainObject extends Object{
	
	public int radius;
	public double xSpeed;
	public double ySpeed;
	public double xAcceleration;
	public double yAcceleration;
	double[][] gravityList = new double[10][2];
	double[] movingListX = new double[3];
	double[] movingListY = new double[3];
	double[] xList = new double[10];
	double[] yList = new double[10];
	public double mass;
	public boolean dead;
	double G = 6.67 * Math.pow(10, -11);
	double multiplier = Math.pow(10, 12);
	//public int radius;
	

	MainObject(int x,int y, int width, int height, int mass) {
		super(x, y, width, height);
		xSpeed = 0;
		ySpeed = 0;
		xAcceleration = 0;
		yAcceleration = 0;
		this.mass = mass;
		//radius = 20;
		dead = false;
	
		
	}
	
	
	
	public void update() {
		if(Gravity_Circle_Demo.gravityPanel.state!=State.DRIFT_MODE) {
		xAcceleration = 0;
		yAcceleration = 0;
		
		for(int i = 0;i<xList.length;i++) {
			xAcceleration += xList[i]/mass;
			yAcceleration += yList[i]/mass;
		}
		
		for(int i = 0;i<movingListX.length;i++) {
			xAcceleration += movingListX[i];
			yAcceleration += movingListY[i];
		}
		
		xSpeed+=xAcceleration;
		//System.out.println("yAccel: "+yAcceleration);
		//System.out.println("ySpeed: "+ySpeed);
		ySpeed+=yAcceleration;
		}
		double max = Gravity_Circle_Demo.gravityPanel.maxSpeed;
		if(Math.sqrt(Math.pow(xSpeed, 2)+Math.pow(ySpeed, 2))>max) {
			double angle = getAngle(xSpeed, ySpeed);
			xSpeed = max * Math.cos(angle);
			ySpeed = max * Math.sin(angle);
		}
		x+=xSpeed;
		y+=ySpeed;
		if(Gravity_Circle_Demo.gravityPanel.wallState == WallBounce.Bounce) {
			if(x>500) {
				xSpeed*=-1;
				x=499;
			}
			if(x<0) {
				xSpeed*=-1;
				x=1;
			}
			if(y>500) {
				ySpeed*=-1;
				y=499;
			}
			if(y<0) {
				ySpeed*=-1;
				y=1;
			}
		}else {
			if(x>500+width||x<0-width||y>500+height||y<0-height) {
				System.out.println("DDDDDIIIIIIEEEEEEEE!!!!!!");
				dead = true;
			}
			
		}
		
	
	}
	
	public double getXForce(int objX, int objY, double objMass) {
		
		//int objX = GravityPanel.getObject().x;
		//int objY = GravityPanel.getObject().y;
		//double objMass = GravityPanel.getObject().mass;
		double r = getDistance((int)x,(int)y,objX, objY);
		double force = G*mass*objMass/Math.pow(r, 2)*multiplier;
		double angle = getAngle(objX-x,objY-y);
		double xForce = getXComp(force, angle);
		//double yForce = getYComp(force, angle);
		//System.out.println(xForce);
		return xForce;
		
	}
	public double getYForce(int objX, int objY, double objMass) {
		//int objX = GravityPanel.getObject().x;
		//int objY = GravityPanel.getObject().y;
		//double objMass = GravityPanel.getObject().mass;
		double r = getDistance((int)x,(int)y,objX, objY);
		double force = G*mass*objMass/Math.pow(r, 2)*multiplier;
		double angle = getAngle(objX-x,objY-y);
		//System.out.println(angle);
		double yForce = getYComp(force, angle);
		//double yForce = getYComp(force, angle);
		
		return yForce;
	}
	
	public double getXAccel(int objX, int objY){
		
		double r = getDistance((int)x,(int)y,objX, objY);
		double accel = G*mass/Math.pow(r, 2)*multiplier;
		double angle = getAngle(objX-x, objY-y);
		
		
		return Math.cos(angle)*accel;
		
	}
	
	public double getYAccel(int objX, int objY){
		double r = getDistance((int)x,(int)y,objX, objY);
		double accel = G*mass/Math.pow(r, 2)*multiplier;
		double angle = getAngle(objX-x, objY-y);
		
		
		return Math.sin(angle)*accel;
	}
	
	public double getDistance(int x1, int y1, int x2, int y2) {
		return  Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
	}

	
	public double getYComp(double force, double angle) {
		//System.out.println("yComp: "+(Math.sin(angle)*force));
		return -Math.sin(angle)*force;
	}
	
	public double getXComp(double force, double angle) {
		
		return -Math.cos(angle)*force;
	}
	
	public double getAngle(double xPull, double yPull) {
		if(xPull==0) {
			if(yPull>0) {
				return Math.PI/2;
			}
			return -Math.PI/2;
		}
		
		if(xPull>0) {
			return Math.atan(((double)(yPull))/(xPull));
		}
		return Math.PI+Math.atan(((double)(yPull))/(xPull));
	}
	
	public void draw(Graphics g) {
		//System.out.println(mass);
		//int radius;
		if(mass>40) {
			radius = width;
		}else {
			radius = (int) (width*mass/40);
			if(radius<2) {
				radius = 2;
			}
		}
		//System.out.println(radius);
		
		g.setColor(Color.BLACK);
		g.fillOval((int)x-radius, (int)y-radius, radius*3, radius*3);
		//System.out.println("Circle is being drawn at x: "+x+" y: "+y);
	}
	
}
