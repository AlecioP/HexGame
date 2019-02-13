package ai.guiSupporter;

import ai.AbsMoveStrategy;
import ai.convertedObjects.Win;
import core.Grid;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

public class GameOverComputer {

	private GameOverComputer() {
		handler = new DesktopHandler(new DLVDesktopService("data/solver/dlv2"));
		filter = new OptionDescriptor();
		filter.addOption("--filter=win/1");
		filter.setSeparator(" ");
	}

	private static GameOverComputer instance;

	public static GameOverComputer getInstance() {
		if(instance==null)
			instance= new GameOverComputer();
		return instance;
	}
	/*(SI)*/

	private Handler handler;
	private String solver = "data/ais/gameOverSolver.asp";
	private OptionDescriptor filter;

	public int isGameOver(Grid grid) throws Exception {
		handler.removeAll();
		handler.addOption(filter);
		AbsMoveStrategy.addCellsFacts(handler, grid);
		InputProgram program = new ASPInputProgram();
		program.addFilesPath(solver);
		handler.addProgram(program);
		ASPMapper.getInstance().registerClass(ai.convertedObjects.Win.class);
		Output out = handler.startSync();
		AnswerSets answers  =(AnswerSets) out;

		/**/
		String error = "OUTPUT ERROR : "+System.lineSeparator();
		error = error +answers.getErrors();
		System.out.println(error);
		System.out.println(answers.getAnswerSetsString());
		for(AnswerSet sol : answers.getAnswersets()){

			for(Object 	atom : sol.getAtoms()) {
				if(! (atom instanceof Win))
					continue;
				Win resp = (Win) atom;
				return resp.getWho();
				
			}
		}
		/**/
		return -1;
	}
}
