package gravityApp;
import java.awt.Color;
import java.awt.Graphics;

public class GravityPin extends Object {
	int mass;
	double G = 6.67 * Math.pow(10, -11);
	double multiplier = Math.pow(10,12);
	public int radius;
	
	public GravityPin(int x, int y, int width, int height, int mass) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.mass = mass;
	}
	
	public void update() {
		
	}
	
	public double getXForce(int objX, int objY, double objMass) {
		
		//int objX = GravityPanel.getObject().x;
		//int objY = GravityPanel.getObject().y;
		//double objMass = GravityPanel.getObject().mass;
		double r = getDistance((int)x,(int)y,objX, objY);
		double force = G*mass*objMass/Math.pow(r, 2)*multiplier;
		double angle = getAngle((int)x,(int)y,objX, objY);
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
		double angle = getAngle((int)x,(int)y,objX, objY);
		//System.out.println(angle);
		double yForce = getYComp(force, angle);
		//double yForce = getYComp(force, angle);
		
		return yForce;
	}
	
	public float getDistance(int x1, int y1, int x2, int y2) {
		return (float) Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
	}
	
	public double getAngle(int x1, int y1, int x2, int y2) {
		if(x2==x1) {
			if(y2-y1<0) {
				return -Math.PI/2;
			}
			return Math.PI/2;
		}
		
		if(x2-x1>0) {
			return Math.atan(((double)(y2-y1))/(x2-x1));
		}
		return Math.PI+Math.atan(((double)(y2-y1))/(x2-x1));
	}
	
	public double getXComp(double force, double angle) {
		
		return -Math.cos(angle)*force;
	}
	
	public double getYComp(double force, double angle) {
		//System.out.println("yComp: "+(Math.sin(angle)*force));
		return -Math.sin(angle)*force;
	}
	
	public void draw(Graphics g) {
		//int radius;
		if(mass>20) {
			radius = width;
		}else {
			radius = (int) (width*mass/20);
			if(radius<2) {
				radius = 2;
			}
		}
		g.setColor(Color.RED);
		g.fillOval((int)x-radius, (int)y-radius, radius*3, radius*3);
	}

}
