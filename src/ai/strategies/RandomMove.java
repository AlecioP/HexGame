package ai.strategies;



import java.util.ArrayList;

import ai.AbsMoveStrategy;
import ai.MoveAdapter;
import core.Coordinate;
import core.Grid;
import core.HexCell;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class RandomMove extends AbsMoveStrategy {

	public RandomMove() {
		AI_PATH = "ais/randomMove.asp";
	}
	
	@Override
	protected int[] doMove(Grid context, Handler handler, ArrayList<MoveAdapter> moves) throws Exception {
		
		/* SUPER METHOD INCLUDES ROLE 
		 * DEFINING PART TO THE PROGRAM */
		
		super.doMove(context, handler, moves);
		
		InputProgram solver = new ASPInputProgram();
//		addFileToProgram(solver, AI_PATH);
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);
		
		
		InputProgram facts = new ASPInputProgram();
		for(int i=0;i<context.getDimension();i++)
			for(int j=0;j<context.getDimension();j++) {
				HexCell cell = new HexCell(new Coordinate(i, j),context);
				cell.setState(context.getGrid()[i][j].getState());
				facts.addObjectInput(cell);
			}
		handler.addProgram(facts);
		
		Output out = handler.startSync();
		
		return AbsMoveStrategy.handleOutput(out);
	}
}
