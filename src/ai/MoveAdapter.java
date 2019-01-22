package ai;

public class MoveAdapter {
	public int[] move;
	
	public MoveAdapter() {
		
	}
	
	public MoveAdapter(int[] move) {
		if(move.length!=2)
			throw new RuntimeException("Move has 2 parameters");
		this.move=move;
	}
}
