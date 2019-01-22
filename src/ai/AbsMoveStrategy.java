package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import ai.convertedObjects.ControlAi;
import ai.convertedObjects.ResponseAi;
import core.Grid;
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
		int[] move = new int[2];
		AnswerSets answers = (AnswerSets) out;
		for(AnswerSet sol : answers.getAnswersets()){
			System.out.println("nuova soluzione");
//			HashSet<Object> atoms =(HashSet<Object>) sol.getAtoms();
//			List<String> stringSol =  sol.getAnswerSet();
			
//			for(String atom : stringSol) {
//				System.out.println("ciao");
//				String[] split = atom.split("[()]");
//				split = split[1].split(",");
//				move[0]=Integer.parseInt(split[0]);
//				move[1]=Integer.parseInt(split[1]);
				
			for(Object 	atom : sol.getAtoms()) {
				if(! (atom instanceof ResponseAi))
					continue;
				ResponseAi resp = (ResponseAi) atom;
				move[0] = resp.getRow();
				move[1] = resp.getCol();
				System.out.println("ciao2");
			}
			break;
		}
		return move;
	}
	
	public static void compute2Bridges(Handler handler) throws Exception {
		String calculator = "ais/bridgeCalculator.asp";
		InputProgram program = new ASPInputProgram();
		addFileToProgram(program, calculator);
		handler.addProgram(program);
	}
}
