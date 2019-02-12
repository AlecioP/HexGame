package ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import core.Grid;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

public class ArtificialIntelligence {

	private Grid context;
	private static Handler handler = null;
	private static String SOLVER_PATH="solver/dlv2.bin.x64";
	private OptionDescriptor options;
	private OptionDescriptor printInput;
	private ArrayList<MoveAdapter> moves;
	private boolean potentialWinLastAiTurn = false;
	
	public ArtificialIntelligence(Grid grid) {
		this.context = grid;
		moves = new ArrayList<MoveAdapter>();
		if(handler==null) {
			handler = new DesktopHandler(new DLVDesktopService(SOLVER_PATH));
			options = new OptionDescriptor();
			options.addOption("--filter=response/2");
			options.setSeparator(" ");
			
			printInput = new OptionDescriptor();
			printInput.addOption("--print-rewriting");
			printInput.setSeparator(" ");
			handler.addOption(printInput);
		}
	}
	
	public void doPlay(AbsMoveStrategy strategy) {
		handler.removeAll();
		
		handler.addOption(options);
		handler.addOption(printInput);
		
		/*Potentially removable*/
		strategy.defineAiRole(2);
		/**/
		
		
		try {
			
			
			AbsMoveStrategy.includeRoleSwap(handler, context);
			
			
			int[] move = strategy.doMove(context, handler,this);
			if(move==null) {
				System.out.println("CANNOT FIND VALID MOVE");
				JOptionPane.showMessageDialog(null, "CANNOT FIND VALID MOVE");
				return;
			}
			System.out.println("Move is : "+move[0]+" "+move[1]);
			
			/** 
			 *  When A.I. controls role 1, the board is rotated to simulate
			 *  A.I. controls role 2.
			 *  For this reason when A.I. controls role 1 the simulated move
			 *  must be converted in a real one, by rotating it.
			 */
			if(strategy.getRole()==2)
				context.occupy(move[0], move[1]);
			else
				context.occupy(move[1], move[0]);
			
			moves.add(new MoveAdapter(move));
			potentialWinLastAiTurn = strategy.hasAiPotentiallyWon(context);
			
		}catch(Exception e) {
			try {
				File out = new File("log/log.txt");
				if(!out.exists())
					out.createNewFile();
				e.printStackTrace();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void addMove(int row,int col) {
		int[] move = new int[2];
		move[0]=row;
		move[1]=col;
		moves.add(new MoveAdapter(move));
	}

	public boolean getPotentialWinLastAiTurn() {
		return potentialWinLastAiTurn;
	}

	public ArrayList<MoveAdapter> getMoves() {
		return moves;
	}
	
	public static void neutraliseHandler() {
		handler = null;
	}
	
}
