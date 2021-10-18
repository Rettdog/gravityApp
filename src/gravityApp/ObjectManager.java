package gravityApp;

import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	ArrayList<GravityPin> gravityPins = new ArrayList<GravityPin>();
	ArrayList<MainObject> movingPins = new ArrayList<MainObject>();
	int maxgravityPins = 10;
	int maxMovingPins = 3;
	double collisionDistance = 10;
	Arrow[][] arrowField = new Arrow[51][51];
	
	public ObjectManager() {
		for(int i = 0;i<arrowField.length;i++) {
			for(int j = 0;j<arrowField[i].length;j++) {
				arrowField[i][j] = new Arrow(10*i,10*j,10,10);
				//System.out.println("i: "+i+" j: "+j);
			}
		}
	}
	public void updateMoving() {
		
		for(int i = 0;i<movingPins.size();i++) {
			for(int j = 0;j<movingPins.size();j++) {
				if(i!=j) {
					movingPins.get(i).movingListX[j] = movingPins.get(i).getXAccel(movingPins.get(j).x,movingPins.get(j).y);
					movingPins.get(i).movingListY[j] = movingPins.get(i).getYAccel(movingPins.get(j).x,movingPins.get(j).y);
				}else {
					movingPins.get(i).movingListX[j] = 0;
					movingPins.get(i).movingListY[j] = 0;
				}
			}
		}
		
		for(MainObject obj : movingPins) {
			obj.update();
		}
		
		for(int i = movingPins.size()-1;i>=0;i--) {
			if(movingPins.get(i).dead) {
				movingPins.remove(i);
				System.out.println("killed");
			}
		}
	}
	
	public void checkCollision() {
		
		boolean[] saveI = new boolean[movingPins.size()];
		for(int i = 0;i<saveI.length;i++) {
			saveI[i] = false;
		}
		for(int i = 0;i<movingPins.size();i++) {
			for(int j = 0;j<movingPins.size();j++) {
				if(i!=j) {
//					if(movingPins.get(i).radius>movingPins.get(j).radius) {
//						//System.out.println(movingPins.get(i).radius);
//						collisionDistance = movingPins.get(i).radius*1.5;
//					}else {
//						collisionDistance = movingPins.get(j).radius*1.5;
//					}
					collisionDistance=5;
					//System.out.println(collisionDistance);
					if(getDistance(movingPins.get(i).x,movingPins.get(i).y,movingPins.get(j).x,movingPins.get(j).y)<collisionDistance*3) {
						if(!saveI[i]) {
						double tempMass = movingPins.get(i).mass;
						
						double v1m1X = movingPins.get(j).xSpeed*movingPins.get(j).mass;
						double v1m1Y = movingPins.get(j).ySpeed*movingPins.get(j).mass;
						double v2m2X = movingPins.get(i).xSpeed*movingPins.get(i).mass;
						double v2m2Y = movingPins.get(i).ySpeed*movingPins.get(j).mass;
						movingPins.get(j).mass += tempMass;
						double m1m2 = movingPins.get(j).mass;
						movingPins.get(j).xSpeed = (v1m1X+v2m2X)/m1m2;
						movingPins.get(j).ySpeed = (v1m1Y+v2m2Y)/m1m2;
						saveI[j] = true;
						movingPins.remove(i);
						}
					}
				}
			}
		}
		
		for(int s = 0;s < gravityPins.size(); s++) {
			for(int m = 0; m < movingPins.size(); m++) {
				if(movingPins.get(m).radius>gravityPins.get(s).radius) {
					collisionDistance = movingPins.get(m).radius*1.5;
				}else {
					collisionDistance = gravityPins.get(s).radius*1.5;
				}
				if(getDistance(gravityPins.get(s).x, gravityPins.get(s).y,movingPins.get(m).x, movingPins.get(m).y)<collisionDistance*1.5) {
					gravityPins.get(s).mass+=movingPins.get(m).mass;
					movingPins.get(m).dead = true;
					
				}
			}
		}
	}
	
	public void addPin(int x, int y, int mass, boolean isMoving) {
		if(isMoving) {
			if(movingPins.size()<maxMovingPins) {
				movingPins.add(new MainObject(x,y,10,10,mass));
			}else {
					System.out.println("Max Moving Pins Reached");
			}
		}else {
			if(gravityPins.size()<maxgravityPins) {
				gravityPins.add(new GravityPin(x,y,10,10,mass));
			}else {
				System.out.println("Max Pins Reached");
			}
		}
//		double[] accelerations = new double[] {0,0};
//		gravityList.add(accelerations);
	}
	
	
	
	public void removePin(int x, int y){
		if(gravityPins.size()==0) {
			return;
		}
		int removeThisPin=0;
		int removeThisMoving = 0;
		float distance = 100;
		float movingDistance = 100;
		for(int i = 0;i<gravityPins.size();i++) {
			float temp = getDistance(x,y,(int)gravityPins.get(i).x,(int)gravityPins.get(i).y);
			if(temp <distance) {
				removeThisPin = i;
				distance = temp;
			}
		}
		for(int i = 0;i<movingPins.size();i++) {
			float temp = getDistance(x,y,(int)movingPins.get(i).x,(int)movingPins.get(i).y);
			if(temp <movingDistance) {
				removeThisMoving = i;
				movingDistance = temp;
			}
		}
		
		if(distance<movingDistance) {
			if(distance<20) {
				gravityPins.remove(removeThisPin);
				for(int j = 0; j<movingPins.size();j++) {
					for(int i = 0;i<gravityPins.size();i++) {
						movingPins.get(j).xList[i]=0;
						movingPins.get(j).yList[i]=0;
					}
				}
			}
		}else if(movingDistance<distance){
			if(movingDistance<20) {
				movingPins.remove(removeThisMoving);
			}
		}
		
	}
	
	public void clearPins() {
		for(int i = gravityPins.size()-1;i>=0;i--) {
			gravityPins.remove(i);
		}
		for(int i = movingPins.size()-1;i>=0;i--) {
			movingPins.remove(i);
		}
	}
	
	public float getDistance(int x1, int y1, int x2, int y2) {
		return (float) Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
	}

	public void updateGravityPins() {
		for(int j = 0; j<movingPins.size();j++) {
			for(int i = 0;i<gravityPins.size();i++) {
				movingPins.get(j).xList[i]=gravityPins.get(i).getXForce((int)movingPins.get(j).x,(int)movingPins.get(j).y,movingPins.get(j).mass);
				movingPins.get(j).yList[i]=gravityPins.get(i).getYForce((int)movingPins.get(j).x,(int)movingPins.get(j).y,movingPins.get(j).mass);
			}
		}
	}
	
	public void updateArrows() {
//		System.out.println(gravityPins.size());
//		System.out.println(movingPins.size());
		for(int i = 0;i<arrowField.length;i++) {
			for(int j = 0;j<arrowField[i].length;j++) {
				for(int k = 0;k<gravityPins.size()+movingPins.size();k++) {
					arrowField[i][j].xList[k] = 0;
					arrowField[i][j].yList[k] = 0;
				}
			
				
			}
		}
		
		for(int i = 0;i<arrowField.length;i++) {
			for(int j = 0;j<arrowField[i].length;j++) {
				for(int k = 0;k<gravityPins.size();k++) {
					arrowField[i][j].xList[k] += gravityPins.get(k).getXForce((int)arrowField[i][j].x, (int)arrowField[i][j].y, 1);
					arrowField[i][j].yList[k] += gravityPins.get(k).getYForce((int)arrowField[i][j].x, (int)arrowField[i][j].y, 1);
				}
				
//				arrowField[i][j].update();
				
			}
		}
		
		for(int i = 0;i<arrowField.length;i++) {
			for(int j = 0;j<arrowField[i].length;j++) {
				for(int k = 0;k<movingPins.size();k++) {
					
					arrowField[i][j].xList[k] += movingPins.get(k).getXForce((int)arrowField[i][j].x, (int)arrowField[i][j].y, 1);
					arrowField[i][j].yList[k] += movingPins.get(k).getYForce((int)arrowField[i][j].x, (int)arrowField[i][j].y, 1);
				}
				
				arrowField[i][j].update();
				
			}
		}
	}
	
	public void drawArrows(Graphics g) {
		for(int i = 0;i<arrowField.length;i++) {
			for(int j = 0;j<arrowField[i].length;j++) {
				arrowField[i][j].draw(g);
			}
		}
	}
	
	
	
	public void drawAll(Graphics g) {
		for(GravityPin pin : gravityPins) {
			pin.draw(g);
		}
		for(MainObject obj : movingPins) {
			obj.draw(g);
		}
	}
	
}
