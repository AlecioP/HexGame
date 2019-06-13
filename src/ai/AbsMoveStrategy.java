package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import ai.convertedObjects.AuxCell;
import ai.convertedObjects.ControlAi;
import ai.convertedObjects.History;
import ai.convertedObjects.LastMove;
import ai.convertedObjects.PotentialWin;
import ai.convertedObjects.ResponseAi;
import core.Coordinate;
import core.Grid;
import core.HexCell;
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

public abstract class AbsMoveStrategy {
	protected String AI_PATH;
	private String ROLE_DEFINE_PATH = "data/ais/roleDefine.asp";
	private int role=0;

	public String getAI_PATH() {
		return AI_PATH;
	}

	public void setAI_PATH(String aI_PATH) {
		AI_PATH = aI_PATH;
	}

	protected abstract int[] doMove(Grid context,Handler handler, ArtificialIntelligence ai) throws Exception ;

	public void includeRoleDefiner(Handler handler) throws Exception{
		if(role!=0) {
			InputProgram facts = new ASPInputProgram();
			facts.addObjectInput(new ControlAi(role));
			handler.addProgram(facts);
		}
		InputProgram definer = new ASPInputProgram();
		//		addFileToProgram(definer, ROLE_DEFINE_PATH);
		definer.addFilesPath(ROLE_DEFINE_PATH);
		handler.addProgram(definer);
		ASPMapper.getInstance().registerClass(ResponseAi.class);

	}

	public void defineAiRole(int role) {
		if(role!=1 && role!=2)
			return;
		this.role = role;
	}

	public int getRole() {
		return this.role;
	}

	public static void addFileToProgram(InputProgram progr,String path) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String line;
		while((line = br.readLine() )!= null) {
			progr.addProgram(line);
			progr.addProgram(System.lineSeparator());
		}
		br.close();
	}

	public static int[] handleOutput(Output out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		String error = "OUTPUT ERROR : "+System.lineSeparator();
		int[] move = null;
		AnswerSets answers = (AnswerSets) out;
		error = error +answers.getErrors();

		System.out.println(error);

		/*DRAFT*/
//		int lastLevelW = Integer.MAX_VALUE;
		/*DRAFT*/
		int converted = answers.getAnswersets().size();
		AnswerSet optimum = answers.getAnswersets().get(converted-1);
//		ArrayList<AnswerSet> restanti = new ArrayList<AnswerSet>(answers.getAnswersets());
		
//		int level = answers.getAnswersets().get(0).getLevelWeight().size()-1;
//		while(level>=0) {
//			int min_w = restanti.get(0).getWeights().get(Integer.valueOf(level));
//			optimum = restanti.get(0);
//			for(AnswerSet x : restanti) {
//				int current_w = x.getWeights().get(Integer.valueOf(level));
//				if(min_w>current_w) {
//					restanti.remove(optimum);
//					optimum = x;
//					min_w = current_w;
//				}
//			}
//			level--;
//		}
		
		System.out.println(optimum);


//		for(AnswerSet sol : answers.getAnswersets()){
//			int current = Integer.MAX_VALUE;
//			if(!sol.getLevelWeight().isEmpty()) {
//				sol.getLevelWeight().get(Integer.valueOf(0)).intValue();
//			}
//			if(current==Integer.MAX_VALUE || current<lastLevelW)
				for(Object 	atom : optimum.getAtoms()) {
					if(! (atom instanceof ResponseAi))
						continue;
					ResponseAi resp = (ResponseAi) atom;
					move = new int[2];
					move[0] = resp.getRow();
					move[1] = resp.getCol();
					break;
				}
			//			break;
//		}
		return move;
	}

	public static int compute2Bridges(Handler handler) throws Exception {
		String calculator = "data/ais/bridgeCalculator.asp";
		InputProgram program = new ASPInputProgram();
		//		addFileToProgram(program, calculator);
		program.addFilesPath(calculator);
		return handler.addProgram(program);
	}

	public static void computeWalls(Handler handler) throws Exception {
		String calculator = "data/ais/wallCalculator.asp";
		InputProgram program = new ASPInputProgram();
		//		addFileToProgram(program, calculator);
		program.addFilesPath(calculator);
		handler.addProgram(program);
	}

	public static void computeRopes(Handler handler) throws Exception {
		String calculator = "data/ais/ropeCalculator.asp";
		InputProgram program = new ASPInputProgram();
		//		addFileToProgram(program, calculator);
		program.addFilesPath(calculator);
		handler.addProgram(program);
	}

	public static void addCellsFacts(Handler handler, Grid context) throws Exception {
		InputProgram facts = new ASPInputProgram();
		for(int i=0;i<context.getDimension();i++)
			for(int j=0;j<context.getDimension();j++) {
				HexCell cell = new HexCell(new Coordinate(i, j),context);
				cell.setState(context.getGrid()[i][j].getState());
				facts.addObjectInput(cell);
			}
		handler.addProgram(facts);
	}

	public static int addAuxCellsFacts(Handler handler, Grid context) throws Exception {
		InputProgram facts = new ASPInputProgram();
		for(int i=0;i<context.getDimension();i++)
			for(int j=0;j<context.getDimension();j++) {
				HexCell cell = new HexCell(new Coordinate(i, j),context);
				cell.setState(context.getGrid()[i][j].getState());
				AuxCell aux = new AuxCell(cell);
				facts.addObjectInput(aux);
			}
		return handler.addProgram(facts);
	}

	public static void addHistoryFacts(Handler handler, ArrayList<MoveAdapter> moves,int firstPlayer) throws Exception {
		if(firstPlayer!=1 && firstPlayer!=2)
			throw new RuntimeException("ERROR : The first player could only be either Player-1 or Player-2");
		InputProgram facts = new ASPInputProgram();
		int role = firstPlayer-1;
		for(int it=0;it<moves.size();it++) {
			role = (role+1)%2;
			History h = new History(it, moves.get(it).move[0], moves.get(it).move[1], role+1);
			facts.addObjectInput(h);
		}

		LastMove lm = new LastMove(moves.size()-1);

		facts.addObjectInput(lm);

		handler.addProgram(facts);
	}

	public boolean hasAiPotentiallyWon(Grid grid) throws Exception{
		Handler handler = new DesktopHandler(new DLVDesktopService("data/solver/dlv2"));
		//moves is null because they are useless
		includeRoleDefiner(handler);
		AbsMoveStrategy.addAuxCellsFacts(handler, grid);

		AbsMoveStrategy.includeRoleSwap(handler, grid);

		AbsMoveStrategy.compute2Bridges(handler);
		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath("data/ais/potentialWinCalculator.asp");
		handler.addProgram(solver);

		/*OPTIONS*/
		OptionDescriptor opt1 = new OptionDescriptor();
		opt1.addOption("--filter=potentialWin/0");
		opt1.setSeparator(" ");
		handler.addOption(opt1);
		OptionDescriptor options = new OptionDescriptor();
		options.addOption("--print-rewriting");
		options.setSeparator(" ");
		handler.addOption(options);
		/*OPTIONS*/
		ASPMapper.getInstance().registerClass(PotentialWin.class);

		Output out = handler.startSync();
		AnswerSets answers = (AnswerSets) out;
		String error = "OUTPUT ERROR : "+System.lineSeparator();
		error = error +answers.getErrors();
		System.out.println(answers.getAnswerSetsString());
		//		System.out.println(error);

		for(AnswerSet as : answers.getAnswersets()) {
			//			System.out.println("ANSWER :"+System.lineSeparator()+as.toString());
			for(Object 	atom : as.getAtoms()) {
				if( atom instanceof PotentialWin)
					return true;
			}
			//useless but...
			break;
		}
		return false;
	}

	public static int[] computeSmartMove(Handler handler, Grid context) throws Exception{
		/**
		 *  This method returns an array of 2 int representing a "smartMove", or null 
		 *  if there isn't any "smartMove" possible. 
		 *  A "smartMove" is that move witch triggers a potential sure win.
		 *  
		 *  This method doesn't affect the state of the handler.
		 *  For this reason every program inserted is tracked and then removed from
		 *  the handler.
		 */


		ArrayList<Integer> added = new ArrayList<Integer>();
		int id3 = AbsMoveStrategy.addAuxCellsFacts(handler, context);
		added.add(Integer.valueOf(id3));

		/* 

		"bridgeDouble" instances are now computed in "smartMove.asp"
		int id4 = AbsMoveStrategy.compute2Bridges(handler);
		added.add(Integer.valueOf(id4));

		 */


		InputProgram potWinSolver = new ASPInputProgram();
		potWinSolver.addFilesPath("data/ais/potentialWinCalculator.asp");
		int id1 = handler.addProgram(potWinSolver);
		added.add(Integer.valueOf(id1));


		InputProgram solver = new ASPInputProgram();
		solver.addFilesPath("data/ais/smartMove.asp");
		int id2 = handler.addProgram(solver);
		added.add(Integer.valueOf(id2));

		Output out = handler.startSync();

		//restore the state of the handler
		for(Integer n:added) {
			int index = n.intValue();
			handler.removeProgram(index);
		}

		/** smartMove.asp creates an instance of "response(Row,Column)" only if that move
		 *  triggers a potential win. Otherwise there won't be any instance of "response"
		 *  and then the returned array will be null.
		 */

		return AbsMoveStrategy.handleOutput(out);
	}

	public static int includeRoleSwap(Handler handler, Grid context) throws Exception{
		InputProgram swapper = new ASPInputProgram();
		swapper.addFilesPath("data/ais/boardSwap.asp");
		AbsMoveStrategy.addAuxCellsFacts(handler, context);
		return handler.addProgram(swapper);
	}
	
	public static int computeAdjacentCells(Handler handler) throws Exception {
		InputProgram program = new ASPInputProgram();
		program.addFilesPath("data/ais/adjacentCells.asp");
		return handler.addProgram(program);
	}

}
