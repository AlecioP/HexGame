package ai.convertedObjects;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("ai")
public class ControlAi {
	@Param(0)
	private int ai;
	
	public ControlAi() {}
	
	public ControlAi(int ai) {
		this.ai=ai;
	}

	public int getAi() {
		return ai;
	}

	public void setAi(int ai) {
		this.ai = ai;
	}
	
	
}
