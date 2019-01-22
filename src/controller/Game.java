package controller;



import javax.swing.JFrame;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import core.Grid;
import view.GridView;
import view.support.ListenerHex;

public class Game{

	private JFrame mainframe;
	private Grid grid;
	private GridView gridview;
	private ListenerHex lis;
	private ArtificialIntelligence ai;
	private AbsMoveStrategy strategy;

	public Game(JFrame mainframe) {
		this.mainframe=mainframe;
		this.mainframe.getContentPane().removeAll();
		grid = new Grid(11);
		gridview = new GridView(mainframe, grid);
		this.mainframe.add(gridview);
		gridview.setVisible(true);
		this.mainframe.setVisible(true);
		
		ai = new ArtificialIntelligence(grid);
		lis = new ListenerHex(gridview,ai,strategy);
	}
	
	public void play() {
		gridview.addMouseListener(lis);
	}
	
	public void configureStrategy(AbsMoveStrategy str) {
		strategy = str;
		lis.setStr(strategy);
	}
}
