package ai.strategies;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import ai.convertedObjects.UBlock;
import core.Grid;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSets;

public class BlockingMove extends AbsMoveStrategy{

	public BlockingMove() {
		AI_PATH = "data/ais/blockingMove.asp";
	}

	@Override
	protected int[] doMove(Grid context, Handler handler, ArtificialIntelligence ai) throws Exception{

		includeRoleDefiner(handler);

		if(ai.getMoves().size()<2 ) {//OPENING MOVE
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
		
		AbsMoveStrategy.addHistoryFacts(handler, ai.getMoves(), this.getRole());
		
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);

		//Add "auxCell" to avoid redundancy due to "control"
		AbsMoveStrategy.addAuxCellsFacts(handler, context);
		AbsMoveStrategy.computeWalls(handler);

		//		handler.addOption(ai.getOptimum());
		AbsMoveStrategy.computeAdjacentCells(handler);


		//		handler.addOption(ai.getTestfilter());

		if(ai.getLastBlock()!=null) {
			InputProgram factBlock = new ASPInputProgram();
			factBlock.addObjectInput(ai.getLastBlock());
			handler.addProgram(factBlock);
		}
		
		int[] test = AbsMoveStrategy.undoEnemyWin(context);
		if(test!=null)
			return test;
		
		handler.removeOption(0);
		handler.addOption(ai.getPrintInput());
		
		Output out1 = handler.startSync();
		/*STAMPE DI DEBUG*/
		AnswerSets ans = (AnswerSets) out1;
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(ans.getOutput());
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		/**/
		handler.addOption(ai.getOptions());
		Output out = handler.startSync();
		System.out.println(out.getOutput());

		int[] blocco = AbsMoveStrategy.readUblock(out,ai.getLastBlock());

		if(blocco!=null)
			ai.setLastBlock(new UBlock(blocco[0],blocco[1]));
		else
			ai.setLastBlock(null);

		return AbsMoveStrategy.handleOutput(out);
	}
}
