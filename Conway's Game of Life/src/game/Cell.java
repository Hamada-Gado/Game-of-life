package game;

public enum Cell {
	
	ALIVE(1),
	DEAD(0);
	
	int value;
	
	Cell(int value){
		this.value = value;
	}

}
