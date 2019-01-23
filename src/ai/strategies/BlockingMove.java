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
		AI_PATH = "ais/blockingMove.asp";
	}
	
	@Override
	protected int[] doMove(Grid context, Handler handler, ArtificialIntelligence ai) throws Exception{
		
		includeRoleDefiner(handler);
		
		/*TEST*/
//		boolean resp = this.hasAiPotentiallyWon(context);
//		if(resp) {
//			JOptionPane.showMessageDialog(null, "POTENTIAL WIN");
//			
//		}
//		System.out.println("TEST : "+resp);
		/*TEST*/
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
			solver.addFilesPath("ais/avoidUndoWin.asp");
			handler.addProgram(solver);
			Output out = handler.startSync();
			return AbsMoveStrategy.handleOutput(out);
		}
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);
		
		AbsMoveStrategy.addCellsFacts(handler, context);
		
		Output out = handler.startSync();
		
		return AbsMoveStrategy.handleOutput(out);
	}
}
