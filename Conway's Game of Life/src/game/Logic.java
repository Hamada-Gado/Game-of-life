package game;

import java.util.Random;

public class Logic {
	
	// simply pointers to which generation to use
	Cell[][] current_generation;
	private Cell[][] other_generation;
	
	// change between generations so cells are not affected be the change of its neighbors
	private Cell[][] generation1;
	private Cell[][] generation2;
	
	Cell[][] original_generation;

	int cols, rows;
	private Random randGen;

	Logic(int cols, int rows){
		this.cols = cols;
		this.rows = rows;
		
		current_generation = new Cell[cols][rows];
		other_generation = new Cell[cols][rows];
		generation1 = new Cell[cols][rows];
		generation2 = new Cell[cols][rows];
		original_generation = new Cell[cols][rows];
		
		randGen = new Random();
	}
	
	void set() {
		for(int x = 0; x < cols; x++)
			for(int y = 0; y < rows; y++) {
				if(randGen.nextInt(0, 6) == 0) {
					generation1[x][y] = Cell.ALIVE;
					generation2[x][y] = Cell.ALIVE;
					original_generation[x][y] = Cell.ALIVE;
				}
				else {
					generation1[x][y] = Cell.DEAD;
					generation2[x][y] = Cell.DEAD;				
					original_generation[x][y] = Cell.DEAD;
				}
			}
		
		current_generation = generation1;
		other_generation = generation2;
	}
	
	void reset() {
		for(int x = 0; x < cols; x++)
			for(int y = 0; y < rows; y++) {
					generation1[x][y] = original_generation[x][y];
					generation2[x][y] = original_generation[x][y];
			}
		
		current_generation = generation1;
		other_generation = generation2;
	}
	
	void update() {
		int neighbors;
		for(int x = 0; x < cols; x++) {
			for(int y = 0; y < rows; y++) {
				neighbors = number_of_neighbors(x, y);
				
				if(current_generation[x][y] == Cell.ALIVE && (neighbors < 2 || neighbors > 3)) {
					other_generation[x][y] = Cell.DEAD;
				}
				else if(current_generation[x][y] == Cell.DEAD && neighbors == 3){
					other_generation[x][y] = Cell.ALIVE;
				}
				else {
					other_generation[x][y] = current_generation[x][y];
				}
			}
		}
		
		current_generation = (current_generation == generation1) ? generation2 : generation1;
		other_generation = (current_generation == generation1) ? generation2 : generation1;
		
	}
	
	int number_of_neighbors(int x, int y) {
		
		int sum = 0;
		int col, row;
		
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				col = (x + i + cols) % cols;
				row = (y + j + rows) % rows;
				if(col == x && row == y)
					continue;
				sum += current_generation[col][row].getValue();
			}
		}		
		return sum;
	}
	
	

}
