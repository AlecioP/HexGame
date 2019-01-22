package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import ai.convertedObjects.ControlAi;
import ai.convertedObjects.History;
import ai.convertedObjects.LastMove;
import ai.convertedObjects.ResponseAi;
import core.Coordinate;
import core.Grid;
import core.HexCell;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;

public abstract class AbsMoveStrategy {
	protected String AI_PATH;
	private String ROLE_DEFINE_PATH = "ais/roleDefine.asp";
	private int role=0;

	public String getAI_PATH() {
		return AI_PATH;
	}

	public void setAI_PATH(String aI_PATH) {
		AI_PATH = aI_PATH;
	}
	
	protected int[] doMove(Grid context,Handler handler,ArrayList<MoveAdapter> moves) throws Exception {
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
		
		return null;
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
		int[] move = new int[2];
		AnswerSets answers = (AnswerSets) out;
		error = error +answers.getErrors();
		
		System.out.println(error);
		for(AnswerSet sol : answers.getAnswersets()){
				
			for(Object 	atom : sol.getAtoms()) {
				if(! (atom instanceof ResponseAi))
					continue;
				ResponseAi resp = (ResponseAi) atom;
				move[0] = resp.getRow();
				move[1] = resp.getCol();
				
			}
			break;
		}
		return move;
	}
	
	public static void compute2Bridges(Handler handler) throws Exception {
		String calculator = "ais/bridgeCalculator.asp";
		InputProgram program = new ASPInputProgram();
//		addFileToProgram(program, calculator);
		program.addFilesPath(calculator);
		handler.addProgram(program);
	}
	
	public static void computeWalls(Handler handler) throws Exception {
		String calculator = "ais/wallCalculator.asp";
		InputProgram program = new ASPInputProgram();
//		addFileToProgram(program, calculator);
		program.addFilesPath(calculator);
		handler.addProgram(program);
	}
	
	public static void computeRopes(Handler handler) throws Exception {
		String calculator = "ais/ropeCalculator.asp";
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
}
