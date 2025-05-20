import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Minesweeper game implementation with a more modern, modular design.
 */
public class Minesweeper extends JFrame {
    private final int rows;
    private final int cols;
    private final int totalBombs;
    private int safeCellsRemaining;

    private final CellButton[][] buttons;
    private final boolean[][] bombLocations;
    private final int[][] neighborBombCount;

    /**
     * Constructor to initialize the game with default size and bombs.
     */
    public Minesweeper() {
        this(10, 10, 25);
    }

    /**
     * Constructor to initialize the game with specified size and bombs.
     */
    public Minesweeper(int rows, int cols, int bombs) {
        super("Minesweeper");
        this.rows = rows;
        this.cols = cols;
        this.totalBombs = bombs;
        this.safeCellsRemaining = rows * cols - bombs;

        // Initialize grid data
        bombLocations = new boolean[rows][cols];
        neighborBombCount = new int[rows][cols];

        // Initialize button grid
        buttons = new CellButton[rows][cols];

        // Setup game UI
        setupFrame();
        generateBombs();
        calculateNeighborCounts();
        createButtons();
        setVisible(true);
    }

    /**
     * Sets up the main window properties.
     */
    private void setupFrame() {
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(rows, cols));
        setResizable(false);
    }

    /**
     * Randomly assign bombs to the grid.
     */
    private void generateBombs() {
        Random rand = new Random();
        int bombsPlaced = 0;

        while (bombsPlaced < totalBombs) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!bombLocations[r][c]) {
                bombLocations[r][c] = true;
                bombsPlaced++;
            }
        }
    }

    /**
     * Calculates number of neighboring bombs for each cell.
     */
    private void calculateNeighborCounts() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                neighborBombCount[r][c] = countBombsAround(r, c);
            }
        }
    }

    /**
     * Counts bombs around a specific cell.
     */
    private int countBombsAround(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int newRow = row + dr;
                int newCol = col + dc;
                if (isWithinBounds(newRow, newCol) && bombLocations[newRow][newCol]) {
                    if (!(dr == 0 && dc == 0)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Creates the grid buttons and adds listeners.
     */
    private void createButtons() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                CellButton btn = new CellButton(r, c);
                btn.addActionListener(new CellClickListener());
                buttons[r][c] = btn;
                add(btn);
            }
        }
    }

    /**
     * Helper to check if position is inside grid bounds.
     */
    private boolean isWithinBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    /**
     * Handles cell button clicks.
     */
    private class CellClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CellButton btn = (CellButton) e.getSource();
            int r = btn.row;
            int c = btn.col;

            if (bombLocations[r][c]) {
                revealAllBombs();
                int option = JOptionPane.showConfirmDialog(
                        Minesweeper.this,
                        "Boom! You hit a mine. Play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else {
                revealCell(r, c);
                if (--safeCellsRemaining == 0) {
                    JOptionPane.showMessageDialog(
                            Minesweeper.this,
                            "Congratulations! You won! Play again?",
                            "Victory",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    int option = JOptionPane.showConfirmDialog(
                            Minesweeper.this,
                            "Play again?",
                            "Victory",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
    }

    /**
     * Reveals all bombs when game is lost.
     */
    private void revealAllBombs() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (bombLocations[r][c]) {
                    buttons[r][c].setText("💣");
                }
                buttons[r][c].setEnabled(false);
            }
        }
    }

    /**
     * Reveals a cell and triggers recursive reveal if no neighboring bombs.
     */
    private void revealCell(int r, int c) {
        if (!isWithinBounds(r, c) || !buttons[r][c].isEnabled()) {
            return;
        }
        buttons[r][c].setEnabled(false);
        int count = neighborBombCount[r][c];
        if (count > 0) {
            buttons[r][c].setText(String.valueOf(count));
        } else {
            // If no neighboring bombs, reveal neighbors recursively
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = r + dr;
                    int nc = c + dc;
                    if (isWithinBounds(nr, nc) && (dr != 0 || dc != 0)) {
                        revealCell(nr, nc);
                    }
                }
            }
        }
    }

    /**
     * Resets the game to initial state.
     */
    private void resetGame() {
        // Reset data
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                bombLocations[r][c] = false;
                neighborBombCount[r][c] = 0;
                buttons[r][c].reset();
            }
        }
        safeCellsRemaining = rows * cols - totalBombs;
        generateBombs();
        calculateNeighborCounts();
    }

    /**
     * Custom JButton class to store position info.
     */
    private class CellButton extends JButton {
        final int row;
        final int col;

        public CellButton(int row, int col) {
            this.row = row;
            this.col = col;
            setFont(new Font("Arial", Font.BOLD, 14));
        }

        public void reset() {
            setText("");
            setEnabled(true);
        }
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}
