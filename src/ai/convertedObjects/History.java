package ai.convertedObjects;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("history")
public class History {

	@Param(0)
	private int turn;
	
	@Param(1)
	private int row;
	
	@Param(2)
	private int column;
	
	@Param(3)
	private int role;
	
	public History() {
		
	}
	
	public History(int turn, int row, int column, int role) {
		this.turn = turn;
		this.row = row;
		this.column = column;
		this.role = role;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	
}
