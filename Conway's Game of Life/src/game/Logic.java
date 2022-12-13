package game;

import java.util.Arrays;
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
	
	void newGeneration(String alive, String dead, String delimiter, String data) {
		// TODO debug 'rowSplit sometimes last char is \n'
		
		System.out.println(data);
		
		String[] colSplit = data.split("\n");
		String[] rowSplit = colSplit[0].split(delimiter);
		
		Cell[][] gen1 = new Cell[colSplit.length][rowSplit.length];
		Cell[][] gen2 = new Cell[colSplit.length][rowSplit.length];
		Cell[][] ogGen = new Cell[colSplit.length][rowSplit.length];
		
		for(int x = 0; x < colSplit.length; x++) {
			rowSplit = colSplit[x].split(delimiter);
			for(int y = 0; y < rowSplit.length-1; y++){
//				System.out.println(rowSplit[y] + "end");
				if(rowSplit[y].equals(alive)) {
					gen1[x][y] = Cell.ALIVE;
					gen2[y][y] = Cell.ALIVE;
					ogGen[x][y] = Cell.ALIVE;
				}
				else if(rowSplit[y].equals(dead)) {
					gen1[x][y] = Cell.DEAD;
					gen2[y][y] = Cell.DEAD;
					ogGen[x][y] = Cell.DEAD;	
				}
				else {
					System.out.println(Arrays.toString(rowSplit));
					System.out.println(y + rowSplit[y] + "end");
					// TODO throw exception as the file is not formatted correctly
					System.err.println("not alive not dead what am i?");
				}
			}
		}
		
		cols = colSplit.length;
		rows = rowSplit.length-1;
		
		generation1 = gen1;
		generation2 = gen2;
		original_generation = ogGen;
		
		current_generation = generation1;
		other_generation = generation2;
	}
	
	void newGeneration() {
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
	
	void resetGeneration() {
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
