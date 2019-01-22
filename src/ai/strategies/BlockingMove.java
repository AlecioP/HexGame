package ai.strategies;

import java.util.ArrayList;

import ai.AbsMoveStrategy;
import ai.MoveAdapter;
import core.Grid;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class BlockingMove extends AbsMoveStrategy{

	public BlockingMove() {
		AI_PATH = "ais/blockingMove.asp";
	}
	
	@Override
	protected int[] doMove(Grid context, Handler handler, ArrayList<MoveAdapter> moves) throws Exception{
		
		includeRoleDefiner(handler);
		
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);
		
		AbsMoveStrategy.addCellsFacts(handler, context);
		
		Output out = handler.startSync();
		
		return AbsMoveStrategy.handleOutput(out);
	}
}
