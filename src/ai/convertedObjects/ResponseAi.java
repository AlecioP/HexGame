package ai.convertedObjects;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("response")
public class ResponseAi {
	@Param(0)
	private int row;
	@Param(1)
	private int col;
	
	public ResponseAi() {
		
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	
}
