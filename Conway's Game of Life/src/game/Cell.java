package game;

public enum Cell {
	
	ALIVE(1),
	DEAD(0);
	
	private int value;
	
	Cell(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
