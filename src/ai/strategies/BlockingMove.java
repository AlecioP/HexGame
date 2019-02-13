package ai.strategies;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import core.Grid;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class BlockingMove extends AbsMoveStrategy{

	public BlockingMove() {
		AI_PATH = "data/ais/blockingMove.asp";
	}
	
	@Override
	protected int[] doMove(Grid context, Handler handler, ArtificialIntelligence ai) throws Exception{
		
		includeRoleDefiner(handler);
		
		if(ai.getMoves().size()<2) {//OPENING MOVE
			AbsMoveStrategy.addCellsFacts(handler, context);
			InputProgram solver = new ASPInputProgram();
			solver.addFilesPath("data/ais/openingMove.asp");
			handler.addProgram(solver);
			Output out = handler.startSync();
			return AbsMoveStrategy.handleOutput(out);
			
		}
		
		// AbsMoveStrategy.computeSmartMove doesn't affect the state of the handler
		int[] smartMove = AbsMoveStrategy.computeSmartMove(handler, context);
		if(smartMove != null)
			return smartMove;
		
		boolean opponentTriesToUndoWin = false;
		boolean resp = this.hasAiPotentiallyWon(context);
		boolean potWinLastMove = ai.getPotentialWinLastAiTurn();
		if(resp==false && potWinLastMove==true)
			opponentTriesToUndoWin=true;
		if(opponentTriesToUndoWin) {
			//[...] DO SOMETHING
			AbsMoveStrategy.addCellsFacts(handler, context);
			AbsMoveStrategy.addHistoryFacts(handler, ai.getMoves(), this.getRole());
			AbsMoveStrategy.compute2Bridges(handler);
			InputProgram solver = new ASPInputProgram();
			solver.addFilesPath("data/ais/avoidUndoWin.asp");
			handler.addProgram(solver);
			Output out = handler.startSync();
			return AbsMoveStrategy.handleOutput(out);
		}
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);
		
		//Add "auxCell" to avoid redundancy due to "control"
		AbsMoveStrategy.addAuxCellsFacts(handler, context);
		AbsMoveStrategy.computeWalls(handler);
		
//		handler.removeOption(0);
//		handler.addOption(ai.getOptimum());
		AbsMoveStrategy.computeAdjacentCells(handler);
		Output out = handler.startSync();
		System.out.println(out.getOutput());
		return AbsMoveStrategy.handleOutput(out);
	}
}
