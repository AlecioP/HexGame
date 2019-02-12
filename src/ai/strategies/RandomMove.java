package ai.strategies;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import core.Grid;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class RandomMove extends AbsMoveStrategy {

	public RandomMove() {
		AI_PATH = "data/ais/randomMove.asp";
	}
	
	@Override
	protected int[] doMove(Grid context, Handler handler, ArtificialIntelligence ai) throws Exception {
		
		includeRoleDefiner(handler);
		
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);
		
		AbsMoveStrategy.addCellsFacts(handler, context);
		
		Output out = handler.startSync();
		
		return AbsMoveStrategy.handleOutput(out);
	}
}
