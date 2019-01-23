package ai.convertedObjects;

import core.HexCell;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("auxCell")
public class AuxCell {
	
	@Param(0)
	private int state;
	@Param(1)
	private int row;
	@Param(2)
	private int column;
	
	public AuxCell() {
	}
	
	public AuxCell(int state, int row, int column) {
		this.state = state;
		this.row = row;
		this.column = column;
	}
	
	public AuxCell(HexCell cell) {
		this.state = cell.getState();
		this.row = cell.getRow();
		this.column = cell.getColumn();
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}
