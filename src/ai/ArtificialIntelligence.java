package ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
	private ArrayList<MoveAdapter> moves;
	
	public ArtificialIntelligence(Grid grid) {
		this.context = grid;
		moves = new ArrayList<MoveAdapter>();
		if(handler==null) {
			handler = new DesktopHandler(new DLVDesktopService(SOLVER_PATH));
			options = new OptionDescriptor();
			options.addOption("--filter=response/2");
			options.setSeparator(" ");
		}
	}
	
	public void doPlay(AbsMoveStrategy strategy) {
		handler.removeAll();
		
		handler.addOption(options);
		strategy.defineAiRole(2);
		try {
			int[] move = strategy.doMove(context, handler,moves);
			System.out.println("Move is : "+move[0]+" "+move[1]);
			context.occupy(move[0], move[1]);
			moves.add(new MoveAdapter(move));
			
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
}
