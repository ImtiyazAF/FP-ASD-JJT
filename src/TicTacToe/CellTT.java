/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231197 - Imtiyaz Shafhal Afif
 * 2 - 5026231172 - Mochamad Zhulmi Danovanz H
 * 3 - 5026231136 - Maulana Muhammad Ad-Dzikri
 */
package TicTacToe;

import TicTacToe.SeedTT;

/**
 * The ConnectFour.Cell class models each individual cell of the TTT 3x3 grid.
 */
public class CellTT {  // save as "ConnectFour.Cell.java"
    // Define properties (package-visible)
    /** Content of this cell (CROSS, NOUGHT, NO_SEED) */
    public SeedTT content;
    /** Row and column of this cell, not used in this program */
    int row, col;

    /** Constructor to initialize this cell */
    public CellTT(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = SeedTT.NO_SEEDTT;
    }

    /** Reset the cell content to EMPTY, ready for a new game. */
    public void newGame() {
        this.content = SeedTT.NO_SEEDTT;
    }

    /** The cell paints itself */
    public void paint() {
        // Retrieve the display icon (text) and print
        String icon = this.content.getIcon();
        System.out.print(icon);
    }
}
