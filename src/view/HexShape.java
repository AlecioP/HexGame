package view;


import java.awt.Polygon;
import java.awt.geom.Point2D;


public class HexShape {
	
	private Hexagon hexagon;
	private double x,y;
	
	public HexShape(Hexagon hexagon) {
		this.hexagon = hexagon;
		x=y=0;
	}

	public boolean contains(double x, double y) {
		if(x<this.x || x > this.x+this.hexagon.getSquareWidth())
			return false;
		if(y<this.y || y > this.y+this.hexagon.getSquareHeight())
			return false;
		
		double x_p1L=this.x,
			y_p1L=this.y+(this.hexagon.getSquareHeight()*3/4),
			x_p2L=this.x+(this.hexagon.getSquareWidth()/2),
			y_p2L=this.y+this.hexagon.getSquareHeight();
		
		if(pointMoreLine(x_p1L, y_p1L, x_p2L, y_p2L, x, y))
			return false;
		
		x_p1L=this.x+(this.hexagon.getSquareWidth());
		y_p1L=this.y+(this.hexagon.getSquareHeight()*3/4);
		
		
		if(pointMoreLine(x_p1L, y_p1L, x_p2L, y_p2L, x, y))
			return false;
		
		x_p1L=this.x;
		y_p1L=this.y+(this.hexagon.getSquareHeight()/4);
		x_p2L=this.x+(this.hexagon.getSquareWidth()/2);
		y_p2L=this.y;
		
		if(pointMoreLine(x_p1L, y_p1L, x_p2L, y_p2L, x, y))
			return false;
		
		x_p1L=this.x+(this.hexagon.getSquareWidth());
		y_p1L=this.y+(this.hexagon.getSquareHeight()/4);
		
		
		if(pointMoreLine(x_p1L, y_p1L, x_p2L, y_p2L, x, y))
			return false;
		
		return true;
	}

	public boolean contains(Point2D p) {
		return this.contains(p.getX(), p.getY());
	}

	private static boolean pointMoreLine(double x_p1L, double y_p1L, double x_p2L, double y_p2L, double x, double y) {
		/**
		 * (x - x1) / (x2 - x1) = (y - y1) / (y2 - y1)
		 */
		if(x_p2L-x_p1L==0)
			System.out.println("x by 0 :: "+x_p2L+"-"+x_p1L);
		if(y_p2L-y_p1L==0)
			System.out.println("y by 0 :: "+y_p2L+"-"+y_p1L);
		return (x-x_p1L)/(x_p2L-x_p1L)<(y-y_p1L)/(y_p2L-y_p1L);
	}
	
	public void setLocation(int x,int y) {
		this.x=x;
		this.y=y;
		hexagon.setLocation(x, y);
	}

	public Polygon getHexagon() {
		return hexagon;
	}
}