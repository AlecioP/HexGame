package core;

public class InvalidMoveException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1036151683547585814L;
	
	private String message;
	
	public InvalidMoveException() {
		this.message="";
	}
	
	public InvalidMoveException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
