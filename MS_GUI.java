import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MS_GUI extends JFrame {

	private JPanel mainBoard;
	private MineSweeperBoard MSBoard;

	public MS_GUI() {
		setSize(550, 550);
		setTitle("Minesweeper");
		setResizable(false);

		mainBoard = new JPanel();
		mainBoard.setLayout(new BorderLayout());

		MSBoard = new MineSweeperBoard();
		mainBoard.add(MSBoard);

		mainBoard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		add(mainBoard);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public class MineSweeperBoard extends JPanel implements ActionListener {

		private JButton[][] board;
		private int rows;
		private int columns;
		private Grid grid;
		int[][] countGrid;
		boolean[][] bombGrid;
		boolean[][] visited;
		private int nonBombs;

		public MineSweeperBoard() {
			this.grid = new Grid();
			this.countGrid = grid.getCountGrid();
			this.bombGrid = grid.getBombGrid();

			this.nonBombs = 0;

			grid.displayBomb();
			grid.displayCount();

			this.rows = grid.getNumRows();
			this.columns = grid.getNumColumns();
			setLayout(new GridLayout(rows, columns));

			board = new JButton[rows][columns];

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					board[i][j] = new JButton();
					board[i][j].addActionListener(this);
					board[i][j].setEnabled(true);

					if (bombGrid[i][j] == true) {
						board[i][j].setName("•");
					} else {
						board[i][j].setName("" + countGrid[i][j]);
						nonBombs++;
					}

					this.add(board[i][j]);
				}
			}
		}

		public boolean isValid(int row, int column) {
			return (row >= 0 && row < rows && column >= 0 && column < columns);
		}

		public void promptMessage() {
			int yesNo = JOptionPane.showConfirmDialog(null, "Play Again?", "Yes or No", JOptionPane.YES_NO_OPTION);
			if (yesNo == JOptionPane.YES_OPTION) {
				this.grid = new Grid();
				this.countGrid = grid.getCountGrid();
				this.bombGrid = grid.getBombGrid();

				grid.displayBomb();
				grid.displayCount();

				this.nonBombs = 0;

				this.rows = grid.getNumRows();
				this.columns = grid.getNumColumns();

				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[i].length; j++) {
						board[i][j].setEnabled(true);

						board[i][j].setText("");
						board[i][j].setBackground(null);

						if (bombGrid[i][j] == true) {
							board[i][j].setName("•");
						} else {
							board[i][j].setName("" + countGrid[i][j]);
							nonBombs++;
						}

					}
				}

			} else {
				System.exit(EXIT_ON_CLOSE);
			}
		}

		public void displayLoss() {
			JOptionPane.showMessageDialog(null, "You Lost!");
		}

		public void displayWin() {
			JOptionPane.showMessageDialog(null, "You Won!");
		}

		public void revealAdjCells(int r, int c) {

			if ((r > 0) && !(board[r - 1][c].getName().equals("•"))) {
				board[r - 1][c].setText(board[r - 1][c].getName()); // up

				if (board[r - 1][c].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r - 1][c].setEnabled(false);
				}
			}

			if ((r + 1 < this.rows) && !(board[r + 1][c].getName().equals("•"))) {
				board[r + 1][c].setText(board[r + 1][c].getName()); // down

				if (board[r + 1][c].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r + 1][c].setEnabled(false);
				}

			}

			if ((c + 1 < this.columns) && !(board[r][c + 1].getName().equals("•"))) {
				board[r][c + 1].setText(board[r][c + 1].getName()); // right

				if (board[r][c + 1].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r][c + 1].setEnabled(false);
				}

			}

			if ((c > 0) && !(board[r][c - 1].getName().equals("•"))) {
				board[r][c - 1].setText(board[r][c - 1].getName()); // left

				if (board[r][c - 1].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r][c - 1].setEnabled(false);
				}
			}

			if ((r > 0 && c > 0) && !(board[r - 1][c - 1].getName().equals("•"))) {
				board[r - 1][c - 1].setText(board[r - 1][c - 1].getName()); // main dia left corner

				if (board[r - 1][c - 1].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r - 1][c - 1].setEnabled(false);
				}

			}

			if ((r + 1 < this.rows) && (c + 1 < this.columns) && !(board[r + 1][c + 1].getName().equals("•"))) {
				board[r + 1][c + 1].setText(board[r + 1][c + 1].getName()); // main dia right corner

				if (board[r + 1][c + 1].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r + 1][c + 1].setEnabled(false);
				}
			}

			if ((r + 1 < this.rows) && (c > 0) && !(board[r + 1][c - 1].getName().equals("•"))) {
				board[r + 1][c - 1].setText(board[r + 1][c - 1].getName()); // 2nd dia left corner

				if (board[r + 1][c - 1].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r + 1][c - 1].setEnabled(false);
				}
			}

			if ((r > 0) && (c + 1 < this.columns) && !(board[r - 1][c + 1].getName().equals("•"))) {
				board[r - 1][c + 1].setText(board[r - 1][c + 1].getName()); // 2nd dia right corner

				if (board[r - 1][c + 1].isEnabled()) {
					nonBombs = nonBombs - 1;
					board[r - 1][c + 1].setEnabled(false);
				}
			}

		}

		public void adjacentCells(int row, int col) {
			if (row < 0 || row >= this.rows || col < 0 || col >= this.columns) {
				return;
			}

			if (this.countGrid[row][col] != 0) {
				return;
			}

			if (board[row][col].isEnabled() == false) {
				return;
			}

//			 HEREEEEEEEEEEEEEEEEEE

			board[row][col].setText(board[row][col].getName());
			board[row][col].setEnabled(false);
			System.out.println(nonBombs);
			nonBombs = nonBombs - 1;
			System.out.println(nonBombs);

			adjacentCells(row - 1, col); // up
			adjacentCells(row + 1, col); // down
			adjacentCells(row, col + 1); // right
			adjacentCells(row, col - 1); // left
			adjacentCells(row - 1, col - 1); // main dia top cor
			adjacentCells(row + 1, col + 1); // main dia bottom cor
			adjacentCells(row + 1, col - 1); // sec dia bottom cor
			adjacentCells(row - 1, col + 1); // sec dia top cor

			this.revealAdjCells(row, col);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();

			int row = 0;
			int col = 0;

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == btnClicked) {
						row = i;
						col = j;
						break;
					}
				}
			}

			if (btnClicked.getName().equals("•")) {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[i].length; j++) {
						if (board[i][j].getName().equals("•")) {
							board[i][j].setBackground(Color.red);
						}

						board[i][j].setText(board[i][j].getName());
						board[i][j].setEnabled(false);
					}
				}
				displayLoss();
				promptMessage();
			} else if (btnClicked.getName().equals("0")) {
//				Reveal adjacent 0 cells
				this.adjacentCells(row, col);
			} else {
				btnClicked.setEnabled(false);

				btnClicked.setText(btnClicked.getName());
				System.out.println(nonBombs);
				nonBombs = nonBombs - 1;
				System.out.println(nonBombs);
				if (nonBombs == 0) {
					displayWin();
					promptMessage();
				}
			}
		}
	}

	public static void main(String[] args) {

		new MS_GUI();

	}

}
