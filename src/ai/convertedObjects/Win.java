package ai.convertedObjects;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("win")
public class Win {

	@Param(0)
	private int who;
	
	public Win() {}
	
	public  Win(int who) {
		this.who = who;
	}

	public int getWho() {
		return who;
	}

	public void setWho(int who) {
		this.who = who;
	}
	
}
