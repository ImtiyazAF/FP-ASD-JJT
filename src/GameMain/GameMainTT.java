package GameMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Board.BoardTT;
import Seed.SeedTT;
import State.StateTT;

public class GameMainTT extends JPanel {
    public static final int ROWS = 3; // Jumlah baris papan
    public static final int COLS = 3; // Jumlah kolom papan

    private JButton[][] cells = new JButton[ROWS][COLS]; // Tombol untuk papan permainan
    private SeedTT currentPlayer = SeedTT.CROSSTT; // Pemain saat ini
    private StateTT currentState = StateTT.PLAYINGTT; // Status permainan
    private BoardTT board; // Papan permainan
    private BoardTT.AIPlayer aiPlayer; // AI Pemain

    public GameMainTT() {
        setLayout(new GridLayout(ROWS, COLS)); // Tata letak grid 3x3
        initBoard();
    }

    private void initBoard() {
        board = new BoardTT(); // Inisialisasi papan permainan
        aiPlayer = board.new AIPlayerTableLookup(board); // Inisialisasi AI

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new JButton(" "); // Tombol kosong
                cells[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36)); // Font besar
                final int r = row, c = col; // Koordinat sel
                cells[row][col].addActionListener(new ActionListener() { // Tambahkan listener untuk tombol
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cells[r][c].getText().equals(" ") && currentState == StateTT.PLAYINGTT) {
                            cells[r][c].setText(currentPlayer.getIcon()); // Set simbol pemain
                            board.stepGame(currentPlayer, r, c); // Update status papan
                            updateGameState(r, c); // Periksa status permainan
                            switchPlayer(); // Ganti pemain
                            if (currentState == StateTT.PLAYINGTT) {
                                aiMove(); // Giliran AI setelah pemain
                            }
                        }
                    }
                });
                add(cells[row][col]); // Tambahkan tombol ke panel
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == SeedTT.CROSSTT) ? SeedTT.NOUGHTTT : SeedTT.CROSSTT;
    }

    private void updateGameState(int row, int col) {
        if (board.checkWin(row, col, currentPlayer)) {
            currentState = (currentPlayer == SeedTT.CROSSTT) ? StateTT.CROSS_WONTT : StateTT.NOUGHT_WONTT;
            JOptionPane.showMessageDialog(this, currentPlayer.getIcon() + " won!");
            resetGame();
        } else if (board.isDraw()) {
            currentState = StateTT.DRAWTT;
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        }
    }

    private void resetGame() {
        board.newGame(); // Reset papan permainan
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].setText(" "); // Kosongkan semua sel
            }
        }
        currentPlayer = SeedTT.CROSSTT; // Atur ulang pemain
        currentState = StateTT.PLAYINGTT; // Set ulang status permainan
    }

    // Fungsi untuk AI bergerak
    private void aiMove() {
        if (currentState == StateTT.PLAYINGTT) {
            int[] aiMove = aiPlayer.move(); // AI memilih langkah
            int aiRow = aiMove[0];
            int aiCol = aiMove[1];
            cells[aiRow][aiCol].setText(SeedTT.NOUGHTTT.getIcon()); // Set simbol AI
            board.stepGame(SeedTT.NOUGHTTT, aiRow, aiCol); // Update papan dengan langkah AI
            updateGameState(aiRow, aiCol); // Periksa status permainan setelah AI bergerak
            switchPlayer(); // Ganti pemain
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Tic-Tac-Toe");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new GameMainTT()); // Tambahkan panel utama
                frame.setSize(400, 400); // Ukuran frame
                frame.setLocationRelativeTo(null); // Pusatkan frame
                frame.setVisible(true); // Tampilkan frame
            }
        });
    }
}
