public class Bomb {

    /**
     * Counts the number of bombs in the 8 neighboring cells around (row, col).
     *
     * @param grid The minesweeper grid.
     * @param row  The row index of the target cell.
     * @param col  The column index of the target cell.
     * @return The count of bombs in the surrounding cells.
     */
    public static int countBombsNearby(int[][] grid, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Directions: 8 neighbors (including diagonals)
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int r = row + dr[i];
            int c = col + dc[i];

            // Check boundaries
            if (r >= 0 && r < rows && c >= 0 && c < cols) {
                if (grid[r][c] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    // Example main method to test
    public static void main(String[] args) {
        int[][] minefield = {
            {0, 1, 0},
            {0, 0, 0},
            {1, 0, 0}
        };

        int row = 1;
        int col = 1;

        int bombsNearby = countBombsNearby(minefield, row, col);
        System.out.println("Number of bombs around (" + row + ", " + col + "): " + bombsNearby);
    }
}
