package game;

import java.util.Random;

public class Logic {
	
	// simply pointers to which board to use
	Cell[][] current_board;
	Cell[][] other_board;
	
	// change between boards so cells are not affected be the change of its neighbors
	Cell[][] board1;
	Cell[][] board2;

	final int cols, rows;
	private Random randGen;

	Logic(int cols, int rows){
		this.cols = cols;
		this.rows = rows;
		current_board = new Cell[cols][rows];
		other_board = new Cell[cols][rows];
		board1 = new Cell[cols][rows];
		board2 = new Cell[cols][rows];
		randGen = new Random();
	}

	void set_board() {
		for(int x = 0; x < cols; x++)
			for(int y = 0; y < rows; y++) {
				if(randGen.nextInt(0, 8) == 0) {
					board1[x][y] = Cell.ALIVE;
					board2[x][y] = Cell.ALIVE;
				}
				else {
					board1[x][y] = Cell.DEAD;
					board2[x][y] = Cell.DEAD;				
				}
			}
		
		current_board = board1;
		other_board = board2;
	}
	
	void update() {
		int neighbours;
		for(int x = 0; x < cols; x++) {
			for(int y = 0; y < rows; y++) {
				neighbours = number_of_neighbours(x, y);
				if(current_board[x][y] == Cell.ALIVE && (neighbours < 2 || neighbours > 3)) {
					other_board[x][y] = Cell.DEAD;
				}
				else if(current_board[x][y] == Cell.DEAD && neighbours == 3){
					other_board[x][y] = Cell.ALIVE;
				}
				else {
					other_board[x][y] = current_board[x][y];
				}
			}
		}
		
		current_board = (current_board == board1) ? board2 : board1;
		other_board = (current_board == board1) ? board2 : board1;
		
	}
	
	int number_of_neighbours(int x, int y) {
		
		int sum = 0;
		int col, row;
		
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				col = (x + i + cols) % cols;
				row = (y + j + rows) % rows;
				if(col == x && row == y)
					continue;
				sum += current_board[col][row].value;
			}
		}		
		return sum;
	}
	
	

}
