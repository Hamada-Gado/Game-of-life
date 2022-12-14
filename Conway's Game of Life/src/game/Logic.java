package game;

import java.util.Random;

import exception.LoadPatternException;

public class Logic {

	// change between generations so cells are not affected be the change of its neighbors
	Cell[][] current_generation;
	private Cell[][] other_generation;
	
	// store first pattern
	Cell[][] original_generation;

	boolean isEmpty;

	int cols, rows;
	private Random randGen;

	Logic(int cols, int rows){
		this.cols = cols;
		this.rows = rows;
		
		current_generation = new Cell[cols][rows];
		other_generation = new Cell[cols][rows];
		original_generation = new Cell[cols][rows];
		
		isEmpty = false;
		randGen = new Random();
	}
	
	void newGeneration(String alive, String dead, String delimiter, String data) throws LoadPatternException {

		String[] colSplit = data.split("(\n)|(\r\n)"); // \n or \r\n to work one any os or manually typed patterns
		String[] rowSplit = colSplit[0].split(delimiter);
		
		Cell[][] gen1 = new Cell[colSplit.length][rowSplit.length];
		Cell[][] ogGen = new Cell[colSplit.length][rowSplit.length];
		
		for(int x = 0; x < colSplit.length; x++) {
			rowSplit = colSplit[x].split(delimiter);
			for(int y = 0; y < rowSplit.length; y++) {
				if(rowSplit[y].equals(alive)) {
					gen1[x][y] = Cell.ALIVE;
					ogGen[x][y] = Cell.ALIVE;
				}
				else if(rowSplit[y].equals(dead)) {
					gen1[x][y] = Cell.DEAD;
					ogGen[x][y] = Cell.DEAD;	
				}
				else {
					throw new LoadPatternException("An error occured. The file might not be formatted correctly");
				}
			}
		}
		
		cols = colSplit.length;
		rows = rowSplit.length;
		
		current_generation = gen1;
		original_generation = ogGen;
	}
	
	void newGeneration() {
		
		for(int x = 0; x < cols; x++)
			for(int y = 0; y < rows; y++) {
				if(randGen.nextInt(0, 2) == 0) {
					current_generation[x][y] = Cell.ALIVE;
					other_generation[x][y] = Cell.ALIVE;
					original_generation[x][y] = Cell.ALIVE;
				}
				else {
					current_generation[x][y] = Cell.DEAD;
					other_generation[x][y] = Cell.DEAD;			
					original_generation[x][y] = Cell.DEAD;
				}
			}
	}
	
	void newEmptyGeneration() {
		for(int x = 0;  x < cols; x++)
			for(int y = 0; y < rows; y++) {
				current_generation[x][y] = Cell.DEAD;
				other_generation[x][y] = Cell.DEAD;			
				original_generation[x][y] = Cell.DEAD;
			}
	}
	
	void resetGeneration() {
		for(int x = 0; x < cols; x++)
			for(int y = 0; y < rows; y++) {
					current_generation[x][y] = original_generation[x][y];
			}
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
		
		Cell[][] tmp = current_generation;
		current_generation = other_generation;
		other_generation = tmp;		
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
