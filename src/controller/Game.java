package controller;



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import core.Grid;
import view.GridView;
import view.support.ListenerHex;
import view.support.Menu;

public class Game{

	private JFrame mainframe;
	private Grid grid;
	private GridView gridview;
	private ListenerHex lis;
	private ArtificialIntelligence ai;
	private AbsMoveStrategy strategy;

	public Game(JFrame _mainframe) {
		this.mainframe=_mainframe;
		this.mainframe.getContentPane().removeAll();
		grid = new Grid(11);
		gridview = new GridView(_mainframe, grid);
		this.mainframe.add(gridview);
		gridview.setVisible(true);
		this.mainframe.setVisible(true);
		
		ai = new ArtificialIntelligence(grid);
		lis = new ListenerHex(gridview,ai,strategy);
		gridview.addKeyListener(new KeyAdapter() {
			
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.out.println("ESC PRESSED -> BACK TO MENU");
					// [...] DO SOMETHING 
					ArtificialIntelligence.neutraliseHandler();
					mainframe.dispose();
					Menu m = new Menu();
					m.launch();
				}
			}
		});
		
		gridview.grabFocus();
	}
	
	public void play() {
		gridview.addMouseListener(lis);
	}
	
	public void configureStrategy(AbsMoveStrategy str) {
		strategy = str;
		lis.setStr(strategy);
	}
}
