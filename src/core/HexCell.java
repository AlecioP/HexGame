package core;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class HexCell {
	
	//SIDES
	public static final int LEFT = 0, RIGHT = 1, DOWN_LEFT = 2, DOWN_RIGHT = 3, UP_LEFT = 4, UP_RIGHT = 5;
	private Coordinate coordinates;
	//VALUES
	public static final int EMPTY = 0, P1 = 1, P2 = 2;
	@Param(0)
	private int state;
	private Grid grid;
	@Param(1)
	private int row;
	@Param(2)
	private int column;
	
	
	public HexCell() {}
	
	public HexCell(Coordinate coords,Grid grid) {
		coordinates = coords;
		setState(HexCell.EMPTY);
		this.grid = grid;
		row = coords.getRow();
		column = coords.getCol();
	}
	
	public Coordinate getNeighbour(int side) {
		Coordinate ret;
		switch(side) {
		case LEFT:{
			ret = new Coordinate(this.coordinates.getRow(), this.coordinates.getCol()-1);
			break;
		}
		case RIGHT:{
			ret = new Coordinate(this.coordinates.getRow(), this.coordinates.getCol()+1);
			break;
		}
		case DOWN_LEFT:{
			ret = new Coordinate(this.coordinates.getRow()+1, this.coordinates.getCol());
			break;
		}
		case DOWN_RIGHT:{
			ret = new Coordinate(this.coordinates.getRow()+1, this.coordinates.getCol()+1);
			break;
		}
		case UP_LEFT:{
			ret = new Coordinate(this.coordinates.getRow()-1, this.coordinates.getCol()-1);
			break;
		}
		case UP_RIGHT:{
			ret = new Coordinate(this.coordinates.getRow()-1, this.coordinates.getCol());
			break;
		}
		default:
			return null;
		}
		if(ret.getCol()<0 || ret.getCol()>=grid.getDimension() || 
				ret.getRow()<0 || ret.getRow()>=grid.getDimension())
			return null;
		return ret;
	}
	
	public void occupyCellP1() {
		if(state!=HexCell.EMPTY)
			throw new InvalidMoveException("Move : P1 in "+state+" state cell");
		setState(HexCell.P1);
		
	}
	public void occupyCellP2() {
		if(state!=HexCell.EMPTY)
			throw new InvalidMoveException("Move : P2 in "+state+" state cell");
		setState(HexCell.P2);
	}
	
	public void empty() {
		setState(HexCell.EMPTY);
	}

	public int getState() {
		return state;
	}
	
	public void setState(int value) {
		state = value;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
		coordinates.setRow(row);
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
		coordinates.setCol(column);
	}
	
	
	
}
