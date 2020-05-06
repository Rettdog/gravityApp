package gravityApp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Arrow extends Object{

	public double angle;
	
	double xPull;
	double yPull;
	double arrowTipDistance;
	double arrowTipAngleDifference;
	
	int[] xTips = new int[3];
	int[] yTips = new int[3];
	
	double k = 2;
	double sqrt2 = Math.sqrt(2);
	
	double[] xList = new double[10];
	double[] yList = new double[10];
	
	public Arrow(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		setArrowTipLength();
		setArrowAngleDifference();
		setArrowPoints();
	}
	
	public void update() {
		xPull = 0;
		yPull = 0;
		for(int i = 0;i<xList.length;i++) {
			xPull += xList[i];
			yPull += yList[i];
			xList[i] = 0;
			yList[i] = 0;
		}
		angle = getAngle(xPull, yPull);
		
		
		setArrowTipLength();
		setArrowAngleDifference();
		setArrowPoints();
	}
	
	public double getAngle(double xPull, double yPull) {
		if(xPull==0) {
			if(yPull<0) {
				return Math.PI/2;
			}
			return -Math.PI/2;
		}
		
		if(xPull>0) {
			return Math.atan((yPull)/(xPull));
		}
		return Math.PI+Math.atan((yPull)/(xPull));
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(130,130,130));
		if(xPull==0&&yPull==0) {
			g.fillOval((int)x, (int)y, 1, 1);
		}else {
		
		g.drawLine((int)x, (int)y, (int) (x+width/2*Math.cos(angle)), (int) (y+height/2*Math.sin(angle)));
		Polygon p = new Polygon(xTips, yTips, 3);
		
		//Nialls attempt at arrows
		//g.fillPolygon(new int[] {(int) (x+width/2*Math.cos(angle)),(int) (x+width/2*Math.cos(angle)+5*Math.cos(angle+3*Math.PI/4)),(int) (x+width/2*Math.cos(angle)-5*Math.cos(angle+3*Math.PI/4)) } , new int[] {(int) (y+height/2*Math.sin(angle)),(int) (y+height/2*Math.sin(angle)+5*Math.sin(angle+3*Math.PI/4)),(int) (y+height/2*Math.sin(angle)+5*Math.sin(angle+3*Math.PI/4))}, 3);
		
		//g.fillPolygon(xTips, yTips, 3);
		g.fillPolygon(p);
		}
	}
	
	public void setArrowPoints() {
		xTips[0] = (int) (x+width/2*Math.cos(angle));
		xTips[1] = (int) (x+arrowTipDistance*Math.cos(angle+arrowTipAngleDifference));
		xTips[2] = (int) (x+arrowTipDistance*Math.cos(angle-arrowTipAngleDifference));
		yTips[0] = (int) (y+width/2*Math.sin(angle));
		yTips[1] = (int) (y+arrowTipDistance*Math.sin(angle+arrowTipAngleDifference));
		yTips[2] = (int) (y+arrowTipDistance*Math.sin(angle-arrowTipAngleDifference));
	}
	
	public void setArrowTipLength() {
		arrowTipDistance = Math.sqrt(4*Math.pow(k, 2)-10*k*sqrt2+25);
	}
	
	public void setArrowAngleDifference() {
		arrowTipAngleDifference = Math.atan(k*sqrt2 / (5-k*sqrt2));
	}
	
}