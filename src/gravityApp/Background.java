package gravityApp;
import java.awt.Color;
import java.awt.Graphics;

public class Background extends Object{
	

	public Background(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g) {
		
		g.setColor(new Color(200,200,200));
		
		//g.fillRect(x, y, width, height);
		g.fillRect((int)x, (int)y, width, height);
	}

}
