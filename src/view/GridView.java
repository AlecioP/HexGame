package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.Grid;
import core.HexCell;

public class GridView extends JPanel{
	private static final long serialVersionUID = 2067515529731492721L;
	/**
	 * 
	 */
	
	private JFrame mainframe;
	private Grid grid;
	private HexShape[][] gridView;
//	private int pointoflastline;
//	private int pointofendframe;

	public GridView(JFrame mainframe, Grid grid) {

		this.setBackground(new Color(0, 153, 51));

		this.mainframe = mainframe;
		mainframe.setVisible(true);
		this.grid = grid;
		gridView = new HexShape[grid.getDimension()][grid.getDimension()];

		int cellSquareH = (mainframe.getContentPane().getPreferredSize().height)/grid.getDimension();
		int cellSquareW = mainframe.getContentPane().getPreferredSize().width/grid.getDimension();

		this.adjust();

		for(int i=0;i<grid.getDimension();i++)
			for(int j=0;j<grid.getDimension();j++) {
				gridView[i][j]=new HexShape(new Hexagon(cellSquareW, cellSquareH));
				int locationX = (cellSquareW*j)+((grid.getDimension()-i-1)*(cellSquareW/2));
				double _3_4_cellsquareh = (cellSquareH/4)*3;
				int locationY =(int) _3_4_cellsquareh*i;
				gridView[i][j].setLocation(locationX, locationY);
				
				if(i==grid.getDimension()-1 && j==grid.getDimension()-1) {
					int menuH = mainframe.getSize().height-mainframe.getContentPane().getHeight();
					mainframe.setSize(mainframe.getWidth(), (int)locationY+cellSquareH+menuH);
					System.out.println("DIMENSION : "+mainframe.getSize());
//					pointoflastline = locationY;
//					pointofendframe = locationY+cellSquareH;
				}
			}
	}

	public void adjust() {
		double cellSquareH = (mainframe.getContentPane().getPreferredSize().height)/grid.getDimension();
		int cellSquareW = mainframe.getContentPane().getPreferredSize().width/grid.getDimension();
		int widthD = (grid.getDimension()*cellSquareW)+((grid.getDimension()-1)*(cellSquareW/2));
		int heightD =(int)(/*CAST*/ (grid.getDimension())*		(	((double)cellSquareH)*((double)3/(double)4)	) /*CAST*/);
		int menuH = mainframe.getSize().height-mainframe.getContentPane().getHeight();
		mainframe.getContentPane().setSize(new Dimension(widthD,heightD));
		mainframe.getContentPane().setPreferredSize(new Dimension(widthD, heightD+menuH));
		Container pane = mainframe.getContentPane();
		Dimension frameDim = new Dimension(pane.getWidth(),pane.getHeight()+menuH);
		mainframe.setSize(frameDim);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setStroke(new BasicStroke(3));
		for(int row = 0;row<grid.getDimension();row++) 
			for(int col=0;col<grid.getDimension();col++) {
				g2d.setColor(Color.BLACK);
				g2d.drawPolygon(gridView[row][col].getHexagon());

				switch (grid.getGrid()[row][col].getState()) {
				case HexCell.EMPTY:{
					g2d.setColor(Color.GRAY);
					break;
				}
				case HexCell.P1:{
					g2d.setColor(Color.RED);
					break;
				}
				case HexCell.P2:{
					g2d.setColor(Color.BLUE);
					break;
				}
				default:
					break;
				}

				g2d.fillPolygon(gridView[row][col].getHexagon());
				int[] xpoints = gridView[row][col].getHexagon().xpoints;
				int[] ypoints = gridView[row][col].getHexagon().ypoints;
				if(row==0) {
					g2d.setColor(Color.RED);
					g2d.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
					g2d.drawLine(xpoints[1], ypoints[1], xpoints[2], ypoints[2]);
				}
				if(row==grid.getDimension()-1) {
					g2d.setColor(Color.RED);
					g2d.drawLine(xpoints[3], ypoints[3], xpoints[4], ypoints[4]);
					g2d.drawLine(xpoints[4], ypoints[4], xpoints[5], ypoints[5]);
				}
				if(col==0) {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(xpoints[5], ypoints[5], xpoints[0], ypoints[0]);
					g2d.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
				}
				if(col==grid.getDimension()-1) {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(xpoints[2], ypoints[2], xpoints[3], ypoints[3]);
					g2d.drawLine(xpoints[3], ypoints[3], xpoints[4], ypoints[4]);
				}
				g2d.setColor(Color.BLACK);
//				g2d.drawString("<"+row+","+col+">", xpoints[5], ypoints[5]);
			}
		g2d.setColor(Color.red);
//		g2d.drawString(grid.getTurn(), 0, 50);
//		g2d.drawLine(0, pointoflastline, 600, pointoflastline);
//		g2d.drawLine(0, pointofendframe, 600, pointofendframe);
		
	}

	public HexShape[][] getGridView() {
		return gridView;
	}

	public Grid getGrid() {
		return grid;
	}

	
	
}
