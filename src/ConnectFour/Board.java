package ConnectFour;

import ConnectFour.Cell;
import ConnectFour.Seed;
import ConnectFour.State;

import java.awt.*;
/**
 * The ConnectFour.Board class models the ROWS-by-COLS game board.
 */
public class Board {
    // Define named constants
    public static final int ROWS = 6;  // ROWS x COLS cells
    public static final int COLS = 7;
    // Define named constants for drawing
    public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
    public static final int GRID_WIDTH = 8;  // Grid-line's width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's half-width
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;  // grid lines
    public static final int Y_OFFSET = 1;  // Fine tune for better display

    // Define properties (package-visible)
    /** Composes of 2D array of ROWS-by-COLS ConnectFour.Cell instances */
    Cell[][] cells;

    /** Constructor to initialize the game board */
    public Board() {
        initGame();
    }

    /** Initialize the game objects (run once) */
    public void initGame() {
        cells = new Cell[ROWS][COLS]; // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // Allocate element of the array
                cells[row][col] = new Cell(row, col);
                // Cells are initialized in the constructor
            }
        }
    }

    /** Reset the game board, ready for new game */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame(); // clear the cell content
            }
        }
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        cells[selectedRow][selectedCol].content = player;

        // Check if the player has won
        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Check for a draw (all cells occupied)
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return State.PLAYING; // Still have empty cells
                }
            }
        }

        return State.DRAW; // No empty cells and no winner
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        // Draw the grid-lines
        g.setColor(COLOR_GRID);
        for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
                    CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET,
                    GRID_WIDTH, CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw all the cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);  // ask the cell to paint itself
            }
        }
    }

    /** Drops a piece in the specified column. Returns the row, or -1 if the column is full. */
    public int dropPiece(Seed seed, int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (cells[row][col].content == Seed.NO_SEED) {
                cells[row][col].content = seed; // Place the piece
                return row; // Return the row where the piece was placed
            }
        }
        return -1; // Column is full
    }

    /** Check if the specified player has won after placing a piece. */
    public boolean hasWon(Seed seed, int row, int col) {
        // Check horizontal
        if (countConsecutive(seed, row, col, 0, 1) + countConsecutive(seed, row, col, 0, -1) >= 3) return true;

        // Check vertical
        if (countConsecutive(seed, row, col, 1, 0) >= 3) return true;

        // Check diagonal (\)
        if (countConsecutive(seed, row, col, 1, 1) + countConsecutive(seed, row, col, -1, -1) >= 3) return true;

        // Check anti-diagonal (/)
        if (countConsecutive(seed, row, col, 1, -1) + countConsecutive(seed, row, col, -1, 1) >= 3) return true;

        return false; // No win
    }

    /** Counts consecutive pieces in one direction. */
    private int countConsecutive(Seed seed, int row, int col, int rowDir, int colDir) {
        int count = 0;
        int r = row + rowDir;
        int c = col + colDir;
        while (r >= 0 && r < ROWS && c >= 0 && c < COLS && cells[r][c].content == seed) {
            count++;
            r += rowDir;
            c += colDir;
        }
        return count;
    }

    /** Get the best move for the computer. */
    public int[] getBestMove(Seed seed) {
        // Try to win
        for (int col = 0; col < COLS; col++) {
            int row = dropPiece(seed, col); // Simulate move
            if (row != -1 && hasWon(seed, row, col)) {
                cells[row][col].content = Seed.NO_SEED; // Undo move
                return new int[]{row, col};
            }
            if (row != -1) cells[row][col].content = Seed.NO_SEED; // Undo move
        }

        // Try to block opponent
        Seed opponent = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        for (int col = 0; col < COLS; col++) {
            int row = dropPiece(opponent, col); // Simulate opponent's move
            if (row != -1 && hasWon(opponent, row, col)) {
                cells[row][col].content = Seed.NO_SEED; // Undo move
                return new int[]{row, col}; // Block move
            }
            if (row != -1) cells[row][col].content = Seed.NO_SEED; // Undo move
        }

        // Otherwise, pick a random valid move
        for (int col = 0; col < COLS; col++) {
            int row = dropPiece(seed, col);
            if (row != -1) {
                cells[row][col].content = Seed.NO_SEED; // Undo move
                return new int[]{row, col};
            }
        }
        return null; // No valid moves
    }
}