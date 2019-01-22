package ai.strategies;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import ai.AbsMoveStrategy;
import ai.MoveAdapter;
import core.Grid;
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
		
		includeRoleDefiner(handler);
		
		/*TEST*/
		boolean resp = this.hasAiPotentiallyWon(context);
		if(resp)
			JOptionPane.showMessageDialog(null, "POTENTIAL WIN");
		System.out.println("TEST : "+resp);
		/*TEST*/
		
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath(AI_PATH);
		handler.addProgram(solver);
		
		AbsMoveStrategy.addCellsFacts(handler, context);
		
		Output out = handler.startSync();
		
		return AbsMoveStrategy.handleOutput(out);
	}
}
