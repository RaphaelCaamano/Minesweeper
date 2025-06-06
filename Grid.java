import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

class Grid {
	private int num_rows;
	private int num_columns;
	private int num_bombs;
	private boolean bomb_grid[][];
	private int count_grid[][];

	public static void main(String args[]) {
		Grid grid = new Grid();
		boolean[][] bomb_grid = grid.get_bomb_grid();
		int[][] count_grid = grid.get_count_grid();

		JFrame f = new JFrame("Button Example");
		JButton b = new JButton("Click Here");
		b.setBounds(50, 100, 95, 30);
		f.add(b);
		f.setSize(800, 800);
		f.setLayout(null);
		f.setVisible(true);
		for (int i = 0; i < 5; i++) {
			System.out.println(i);
			JButton b1 = new JButton("Click Here");
			b1.setBounds(500, 100, 95, 30);

			f.add(b1);
		}
	}

	public Grid() {
		num_rows = 10;
		num_columns = 10;
		num_bombs = 25;
		bomb_grid = new boolean[num_rows][num_columns];
		count_grid = new int[num_rows][num_columns];
		create_bomb_grid();
		create_count_grid();
	}

	public Grid(int rows, int columns) {
		num_rows = rows;
		num_columns = columns;
		num_bombs = 25;
		bomb_grid = new boolean[num_rows][num_columns];
		count_grid = new int[num_rows][num_columns];
		create_bomb_grid();
		create_count_grid();
	}

	public Grid(int rows, int columns, int bombs) {
		num_rows = rows;
		num_columns = columns;
		num_bombs = bombs;
		bomb_grid = new boolean[num_rows][num_columns];
		count_grid = new int[num_rows][num_columns];
		create_bomb_grid();
		create_count_grid();
	}

	int get_num_rows() {
		return this.num_rows;
	}

	int get_num_cols() {
		return this.num_columns;
	}

	int get_num_bombs() {
		return this.num_bombs;
	}

	boolean[][] get_bomb_grid() {
		return this.bomb_grid;
	}

	int[][] get_count_grid() {
		return this.count_grid;
	}

// function to check if a given row and column are inside the bounds and is
// a bomb.
	boolean is_valid(int row, int column) {
		if (row >= 0 && column >= 0 && row < num_rows && column < num_columns && bomb_grid[row][column] == true) {
			return true;
		}
		return false;
	}

// function to generate a random number from 0 , max.
	int gen_random(int max) {
		return ThreadLocalRandom.current().nextInt(0, max);
	}

// function to initialise the grid with randomly generated bomb coordinates.
	void create_bomb_grid() {
		for (int i = 0; i < num_rows; i++) {
			for (int j = 0; j < num_columns; j++) {
				bomb_grid[i][j] = false;
			}
		}
		for (int i = 0; i < num_bombs; i++) {
			int x = gen_random(num_rows);
			int y = gen_random(num_columns);
			bomb_grid[x][y] = true;
		}
	}

// function to create a count grid for each row and column , counting the
// num of bombs in all adjacent 8 directions.
	void create_count_grid() {
		for (int i = 0; i < num_rows; i++) {
			for (int j = 0; j < num_columns; j++) {
				int bombs = 0;
				if (is_valid(i, j)) {
					bombs += 1;
				}
				if (is_valid(i, j - 1)) {
					bombs += 1;
				}
				if (is_valid(i - 1, j - 1)) {
					bombs += 1;
				}
				if (is_valid(i - 1, j)) {
					bombs += 1;
				}
				if (is_valid(i - 1, j + 1)) {
					bombs += 1;
				}
				if (is_valid(i, j + 1)) {
					bombs += 1;
				}
				if (is_valid(i + 1, j + 1)) {
					bombs += 1;
				}
				if (is_valid(i, j + 1)) {
					bombs += 1;
				}
				if (is_valid(i + 1, j - 1)) {
					bombs += 1;
				}
				count_grid[i][j] = bombs;
			}
		}
	}
}
