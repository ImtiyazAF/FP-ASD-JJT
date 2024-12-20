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

import java.util.ArrayList;
import java.util.List;

/**
 * The ConnectFour.Board class models the TTT game-board of 3x3 cells.
 */
public class BoardTT {
    // Define named constants for the grid
    public static final int ROWS = 3;
    public static final int COLS = 3;

    // Define properties
    public CellTT[][] cells; // Akses publik untuk GUI (alternatifnya, gunakan getter)

    public CellTT[][] getCells() {
        return cells;
    }

    /** Constructor to initialize the game board */
    public BoardTT() {
        initGame();
    }

    /** Initialize the board (run once) */
    public void initGame() {
        cells = new CellTT[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new CellTT(row, col);
            }
        }
    }

    /** Reset the contents of the game board, ready for a new game. */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();
            }
        }
    }

    /** Check if the current player wins after their move. */
    public boolean checkWin(int selectedRow, int selectedCol, SeedTT player) {
        // 3-in-a-row
        if (cells[selectedRow][0].content == player &&
                cells[selectedRow][1].content == player &&
                cells[selectedRow][2].content == player) {
            return true;
        }
        // 3-in-a-column
        if (cells[0][selectedCol].content == player &&
                cells[1][selectedCol].content == player &&
                cells[2][selectedCol].content == player) {
            return true;
        }
        // 3-in-a-diagonal
        if (selectedRow == selectedCol &&
                cells[0][0].content == player &&
                cells[1][1].content == player &&
                cells[2][2].content == player) {
            return true;
        }
        // 3-in-the-opposite-diagonal
        if (selectedRow + selectedCol == 2 &&
                cells[0][2].content == player &&
                cells[1][1].content == player &&
                cells[2][0].content == player) {
            return true;
        }
        return false;
    }

    /** Check if the board is full (i.e., a draw). */
    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == SeedTT.NO_SEEDTT) {
                    return false; // Masih ada sel kosong
                }
            }
        }
        return true; // Tidak ada sel kosong, seri
    }

    /**
     * Perform a move and update the board.
     * Return the new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public StateTT stepGame(SeedTT player, int selectedRow, int selectedCol) {
        cells[selectedRow][selectedCol].content = player;
        if (checkWin(selectedRow, selectedCol, player)) {
            return (player == SeedTT.CROSSTT) ? StateTT.CROSS_WONTT : StateTT.NOUGHT_WONTT;
        } else if (isDraw()) {
            return StateTT.DRAWTT;
        } else {
            return StateTT.PLAYINGTT;
        }
    }


    /** For debugging: Print the board state in console (optional for GUI). */
    public void paint() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                System.out.print(" ");
                cells[row][col].paint();
                System.out.print(" ");
                if (col < COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----------");
            }
        }
        System.out.println();
    }

    /** AI Classes */
    public abstract class AIPlayer {
        protected int ROWS = GameMainTT.ROWS;
        protected int COLS = GameMainTT.COLS;
        protected CellTT[][] cells;
        protected SeedTT mySeed;
        protected SeedTT oppSeed;

        public AIPlayer(BoardTT board) {
            cells = board.getCells();
        }

        public void setSeed(SeedTT seed) {
            this.mySeed = seed;
            oppSeed = (mySeed == SeedTT.CROSSTT) ? SeedTT.NOUGHTTT : SeedTT.CROSSTT;
        }

        public abstract int[] move();
    }

    public class AIPlayerTableLookup extends AIPlayer {
        private int[][] preferredMoves = {
                {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
                {0, 1}, {1, 0}, {1, 2}, {2, 1}
        };

        public AIPlayerTableLookup(BoardTT board) {
            super(board);
        }

        @Override
        public int[] move() {
            for (int[] move : preferredMoves) {
                if (cells[move[0]][move[1]].content == SeedTT.NO_SEEDTT) {
                    return move;
                }
            }
            throw new IllegalStateException("No empty cell available!");
        }
    }

    class AIPlayerMinimax extends AIPlayer {
        public AIPlayerMinimax(BoardTT board) {
            super(board);
        }

        @Override
        public int[] move() {
            int[] result = minimax(2, mySeed);
            return new int[]{result[1], result[2]};
        }

        private int[] minimax(int depth, SeedTT player) {
            List<int[]> nextMoves = generateMoves();

            int bestScore = (player == mySeed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            int currentScore;
            int bestRow = -1;
            int bestCol = -1;

            if (nextMoves.isEmpty() || depth == 0) {
                bestScore = evaluate();
            } else {
                for (int[] move : nextMoves) {
                    cells[move[0]][move[1]].content = player;
                    if (player == mySeed) {
                        currentScore = minimax(depth - 1, oppSeed)[0];
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestRow = move[0];
                            bestCol = move[1];
                        }
                    } else {
                        currentScore = minimax(depth - 1, mySeed)[0];
                        if (currentScore < bestScore) {
                            bestScore = currentScore;
                            bestRow = move[0];
                            bestCol = move[1];
                        }
                    }
                    cells[move[0]][move[1]].content = SeedTT.NO_SEEDTT;
                }
            }
            return new int[]{bestScore, bestRow, bestCol};
        }

        private List<int[]> generateMoves() {
            List<int[]> nextMoves = new ArrayList<>();
            if (hasWon(mySeed) || hasWon(oppSeed)) {
                return nextMoves;
            }

            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == SeedTT.NO_SEEDTT) {
                        nextMoves.add(new int[]{row, col});
                    }
                }
            }
            return nextMoves;
        }

        private int evaluate() {
            int score = 0;
            score += evaluateLine(0, 0, 0, 1, 0, 2);
            score += evaluateLine(1, 0, 1, 1, 1, 2);
            score += evaluateLine(2, 0, 2, 1, 2, 2);
            score += evaluateLine(0, 0, 1, 0, 2, 0);
            score += evaluateLine(0, 1, 1, 1, 2, 1);
            score += evaluateLine(0, 2, 1, 2, 2, 2);
            score += evaluateLine(0, 0, 1, 1, 2, 2);
            score += evaluateLine(0, 2, 1, 1, 2, 0);
            return score;
        }

        private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
            int score = 0;

            if (cells[row1][col1].content == mySeed) {
                score = 1;
            } else if (cells[row1][col1].content == oppSeed) {
                score = -1;
            }

            if (cells[row2][col2].content == mySeed) {
                score = (score == 1) ? 10 : (score == -1 ? 0 : 1);
            } else if (cells[row2][col2].content == oppSeed) {
                score = (score == -1) ? -10 : (score == 1 ? 0 : -1);
            }

            if (cells[row3][col3].content == mySeed) {
                score = (score > 0) ? score * 10 : (score < 0 ? 0 : 1);
            } else if (cells[row3][col3].content == oppSeed) {
                score = (score < 0) ? score * 10 : (score > 0 ? 0 : -1);
            }

            return score;
        }

        private boolean hasWon(SeedTT thePlayer) {
            int pattern = 0;
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == thePlayer) {
                        pattern |= (1 << (row * COLS + col));
                    }
                }
            }

            int[] winningPatterns = {
                    0b111000000, 0b000111000, 0b000000111,
                    0b100100100, 0b010010010, 0b001001001,
                    0b100010001, 0b001010100
            };

            for (int winningPattern : winningPatterns) {
                if ((pattern & winningPattern) == winningPattern) {
                    return true;
                }
            }
            return false;
        }
    }
}
