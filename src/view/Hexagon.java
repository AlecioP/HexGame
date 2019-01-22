package view;

import java.awt.Polygon;

public class Hexagon extends Polygon {

	private static final long serialVersionUID = -1712905135868526926L;
	/**
	 * 
	 */
	
	private int[] xPoints;
	private int[] yPoints;
	private int squareWidth;
	private int squareHeight;
	
	public Hexagon(int squareWidth,int squareHeight) {
		super();
		this.squareHeight = squareHeight;
		this.squareWidth = squareWidth;
		xPoints = new int[6];
		yPoints = new int[6];
		
		addPoints(0,0);
		
	}
	
	private void addPoints(int x,int y) {
		/*X POINTS*/
		xPoints[0]=xPoints[5]=x;
		xPoints[1]=xPoints[4]=x+this.squareWidth/2;
		xPoints[2]=xPoints[3]=x+this.squareWidth;
		/*Y POINTS*/
		yPoints[0]=yPoints[2]=y+this.squareHeight/4;
		yPoints[1]=y;
		yPoints[3]=yPoints[5]=y+(this.squareHeight/4)*3;
		yPoints[4]=y+this.squareHeight;
		for(int i=0;i<6;i++)
			this.addPoint(xPoints[i], yPoints[i]);
	}

	public int getSquareWidth() {
		return squareWidth;
	}

	public int getSquareHeight() {
		return squareHeight;
	}
	
	public void setLocation(int x,int y) {
		this.reset();
		this.addPoints(x, y);
	}

	public int[] getxPoints() {
		return xPoints;
	}

	public int[] getyPoints() {
		return yPoints;
	}
	
}
