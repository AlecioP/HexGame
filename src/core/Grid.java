package core;

public class Grid {
	private int dimension;
	private String turn;
	private int movesDone;
	public final static String P1_MOVES = "P1 MOVES", P2_MOVES = "P2 MOVES";
	private HexCell[][] grid;
	private Coordinate firstMove=null;
	
	public Grid(int dimension) {
		dimension = Math.abs(dimension);
		this.dimension = dimension;
		turn = P1_MOVES;
		grid = new HexCell[this.dimension][this.dimension];
		for(int i=0;i<this.dimension;i++)
			for(int j=0;j<this.dimension;j++)
				grid[i][j] = new HexCell(new Coordinate(i, j), this);
		this.movesDone = 0;
	}
	
	public void occupy(int row,int col) {
		if(firstMove==null)
			firstMove = new Coordinate(row, col);
		if(grid[row][col].getState()!=HexCell.EMPTY) {
			pieRule();
			return;
		}
		if(turn.equals(P1_MOVES))
			grid[row][col].occupyCellP1();
		else
			grid[row][col].occupyCellP2();
		movesDone++;
		changeTurn();
	}

	public  int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	private void changeTurn() {
		if(turn.equals(P1_MOVES))
			turn = P2_MOVES;
		else
			turn = P1_MOVES;
	}
	
	public String getTurn() {
		return turn;
	}
	
	private void pieRule() {
		if(movesDone!=1)
			throw new InvalidMoveException("PIE RULE APPLIES ONLY TO THE FIRST TURN OF THE SECOND PLAYER");
		int sum = firstMove.getRow()+firstMove.getCol();
		int diff;
		grid[firstMove.getRow()][firstMove.getCol()].empty();
		int r=0,c=0;
		if(sum>=dimension-1) {
			diff = sum-(dimension-1);
			r = firstMove.getRow()-diff;
			c = firstMove.getCol()-diff;
			
		}else {
			diff = (dimension-1)-sum;
			r = firstMove.getRow()+diff;
			c = firstMove.getCol()+diff;
		}
		occupy(r, c);
	}

	public HexCell[][] getGrid() {
		return grid;
	}
	
	
}
